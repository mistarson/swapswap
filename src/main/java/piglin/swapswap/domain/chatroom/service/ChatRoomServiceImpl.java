package piglin.swapswap.domain.chatroom.service;

import jakarta.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.chatroom.dto.ChatRoomResponseDto;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.chatroom.mapper.ChatRoomMapper;
import piglin.swapswap.domain.chatroom.repository.ChatRoomRepository;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.service.MemberService;
import piglin.swapswap.domain.member.service.MemberServiceImplV1;
import piglin.swapswap.domain.message.constant.MessageType;
import piglin.swapswap.domain.message.dto.request.MessageRequestDto;
import piglin.swapswap.domain.message.dto.response.MessageResponseDto;
import piglin.swapswap.domain.message.entity.Message;
import piglin.swapswap.domain.message.mapper.MessageMapper;
import piglin.swapswap.domain.message.service.MessageServiceImpl;
import piglin.swapswap.domain.notification.constant.NotificationType;
import piglin.swapswap.domain.notification.service.NotificationService;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberServiceImplV1 memberService;
    private final MessageServiceImpl messageService;
    private final NotificationService notificationService;
    private final SimpMessageSendingOperations sendingOperations;

    @Override
    public ChatRoomResponseDto getChatRoomResponseDto(Long roomId, Long memberId) {

        ChatRoom chatRoom = getChatRoom(roomId);
        String nickname = memberService.getMember(getOtherMemberId(chatRoom, memberId))
                .getNickname();

        return ChatRoomMapper.getChatRoomResponseDto(chatRoom, nickname);
    }

    @Override
    public List<ChatRoomResponseDto> getChatRoomList(Member member) {

        List<ChatRoom> chatRoomList = chatRoomRepository.findAllByMemberId(member.getId());

        return getChatRoomResponseDtoList(chatRoomList, member.getId());
    }

    @Override
    @Transactional
    public Long createChatroom(Member firstMember, Long secondMemberId) {

        Member secondMember = memberService.getMember(secondMemberId);

        ChatRoom chatRoom = chatRoomRepository.findChatRoomByMemberIds(firstMember.getId(),
                        secondMember.getId())
                .orElseGet(() -> chatRoomRepository.save(
                        ChatRoomMapper.createChatRoom(firstMember, secondMember)));

        reentryMember(firstMember, chatRoom);

        return chatRoom.getId();
    }

    @Override
    public List<MessageResponseDto> getMessageByChatRoomId(Long roomId, Member member) {

        ChatRoom chatRoom = getChatRoom(roomId);
        validateMember(member, chatRoom);

        List<Message> messageList = messageService.findAllByChatRoomId(roomId);

        return MessageMapper.messageToMessageDto(messageList);
    }

    @Override
    @Transactional
    public void saveMessage(MessageRequestDto requestDto) {

        ChatRoom chatRoom = getChatRoom(requestDto.chatRoomId());
        Member sender = memberService.getMember(requestDto.senderId());

        Message message = MessageMapper.createMessage(sender, chatRoom, requestDto);
        chatRoom.updateChatRoom(requestDto.text());

        messageService.saveMessage(message);

        Long receiverId = chatRoom.getFirstMemberId().equals(requestDto.senderId()) ? chatRoom.getSecondMemberId() : chatRoom.getFirstMemberId();
        Member receiver = memberService.getMember(receiverId);

        String Url = "http://swapswap.shop/chats/room/" + chatRoom.getId();
        String content = receiver.getNickname() + "님! " + sender.getNickname() + "님으로부터 채팅이 왔어요!";
        notificationService.send(receiver, NotificationType.DEAL, content, Url);
    }

    @Override
    @Transactional
    public void leaveChatRoom(Member member, Long roomId) {

        ChatRoom chatRoom = getChatRoom(roomId);

        chatRoom.leaveChatRoom(member);

        if (chatRoom.isLeaveFirstMember() && chatRoom.isLeaveSecondMember()) {

            chatRoom.deleteChatRoom();
            messageService.deleteMessage(chatRoom);
        }
    }

    private ChatRoom getChatRoom(Long roomId) {

        return chatRoomRepository.findByIdAndIsDeletedFalse(roomId).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_FOUND_CHATROOM_EXCEPTION));
    }

    private void validateMember(Member member, ChatRoom chatRoom) {

        if (!(member.getId().equals(chatRoom.getFirstMemberId()) && !chatRoom.isLeaveFirstMember())
                &&
                !(member.getId().equals(chatRoom.getSecondMemberId())
                        && !chatRoom.isLeaveSecondMember())) {

            throw new BusinessException(ErrorCode.NOT_CHAT_ROOM_MEMBER_EXCEPTION);
        }
    }

    private void reentryMember(Member member, ChatRoom chatRoom) {

        if (member.getId().equals(chatRoom.getFirstMemberId()) && chatRoom.isLeaveFirstMember()) {

            sendEnterMessage(member, chatRoom);
            chatRoom.reentryFirstMember();
        }

        if (member.getId().equals(chatRoom.getSecondMemberId()) && chatRoom.isLeaveSecondMember()) {

            sendEnterMessage(member, chatRoom);
            chatRoom.reentrySecondMember();
        }
    }

    private void sendEnterMessage(Member member, ChatRoom chatRoom) {

        sendingOperations.convertAndSend(
                "/queue/chat/room" + chatRoom.getId(),
                MessageMapper.createMessageRequestDto(chatRoom.getId(), member.getId(),
                        MessageType.ENTER, member.getNickname() + "님이 입장하셨습니다.")
        );
    }

    private List<ChatRoomResponseDto> getChatRoomResponseDtoList(List<ChatRoom> chatRoomList,
            Long memberId) {

        List<Long> otherMemberIds = chatRoomList.stream()
                .map(chatRoom -> getOtherMemberId(chatRoom, memberId)).toList();

        List<Member> otherMembers = memberService.getMembers(otherMemberIds);

        Map<Long, String> otherMemberIdToNicknameMap = otherMembers.stream()
                .collect(Collectors.toMap(Member::getId, Member::getNickname));

        return chatRoomList.stream()
                .sorted(Comparator.comparing(ChatRoom::getLastMessageTime,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .map(chatRoom -> {
                    Long otherMemberId = getOtherMemberId(chatRoom, memberId);
                    String otherMemberNickname = otherMemberIdToNicknameMap.get(otherMemberId);

                    return ChatRoomResponseDto.builder()
                            .id(chatRoom.getId())
                            .nickname(otherMemberNickname)
                            .lastMessage(chatRoom.getLastMessage())
                            .lastMessageTime(chatRoom.getLastMessageTime())
                            .build();
                })
                .toList();
    }

    private Long getOtherMemberId(ChatRoom chatRoom, Long memberId) {
        if (chatRoom.getFirstMemberId().equals(memberId)) {
            return chatRoom.getSecondMemberId();
        } else {
            return chatRoom.getFirstMemberId();
        }
    }
}