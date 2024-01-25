package piglin.swapswap.domain.chatroom.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.chatroom.dto.ChatRoomResponseDto;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.chatroom.mapper.ChatRoomMapper;
import piglin.swapswap.domain.chatroom.repository.ChatRoomRepository;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.service.MemberServiceImplV1;
import piglin.swapswap.domain.message.dto.request.MessageRequestDto;
import piglin.swapswap.domain.message.dto.response.MessageResponseDto;
import piglin.swapswap.domain.message.entity.Message;
import piglin.swapswap.domain.message.mapper.MessageMapper;
import piglin.swapswap.domain.message.service.MessageServiceImpl;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberServiceImplV1 memberService;
    private final MessageServiceImpl messageService;

    @Override
    public List<ChatRoomResponseDto> getChatRoomList(Member member) {

        List<ChatRoom> chatRoomList = chatRoomRepository.findAllByMember(member);

        return ChatRoomMapper.getChatRoomResponseDtoList(chatRoomList);
    }

    @Override
    public Long createChatroom(Member member, Long secondMemberId) {

        Member member2 = memberService.getMember(secondMemberId);

        ChatRoom chatRoom = chatRoomRepository.findChatRoomByMembers(member, member2).orElseGet(() ->
                createChatRoom(member, member2));

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

        ChatRoom chatRoom = getChatRoomById(requestDto.chatRoomId());
        Member member = memberService.getMemberByNickname(requestDto.senderNickname());

        Message message = createMessageAndUpdateLastMessage(chatRoom, requestDto, member);

        messageService.saveMessage(message);
    }

    @Override
    @Transactional
    public void leaveChatRoom(Member member, Long roomId) {

        ChatRoom chatRoom = getChatRoom(roomId);

        chatRoom.leaveChatRoom(member);

        if (chatRoom.getMember1() == null && chatRoom.getMember2() == null) {

            chatRoom.deleteChatRoom();
            messageService.deleteMessage(chatRoom);
        }
    }

    private ChatRoom getChatRoom(Long roomId) {

        return chatRoomRepository.findById(roomId).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_FOUND_CHATROOM_EXCEPTION));
    }

    private ChatRoom getChatRoomById(Long roomId) {

        return chatRoomRepository.findById(roomId).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_FOUND_CHATROOM_EXCEPTION)
        );
    }

    private ChatRoom createChatRoom(Member member1, Member member2) {

        ChatRoom chatRoom = ChatRoomMapper.createChatRoom(member1, member2);

        return chatRoomRepository.save(chatRoom);
    }

    private Message createMessageAndUpdateLastMessage(ChatRoom chatRoom, MessageRequestDto requestDto, Member member) {

        Message message = MessageMapper.createMessage(member, chatRoom, requestDto);
        chatRoom.updateChatRoom(requestDto.text());

        return message;
    }

    private void validateMember(Member member, ChatRoom chatRoom) {

        Member member1 = chatRoom.getMember1();
        Member member2 = chatRoom.getMember2();

        if (!(member1 != null && member.getId().equals(member1.getId())) && !(member2 != null && member.getId().equals(member2.getId()))) {
            throw new BusinessException(ErrorCode.NOT_CHAT_ROOM_MEMBER_EXCEPTION);
        }
    }

}