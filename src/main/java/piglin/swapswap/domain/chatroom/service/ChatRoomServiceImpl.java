package piglin.swapswap.domain.chatroom.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import piglin.swapswap.domain.chatroom.dto.ChatRoomResponseDto;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.chatroom.mapper.ChatRoomMapper;
import piglin.swapswap.domain.chatroom.repository.ChatRoomRepository;
import piglin.swapswap.domain.chatroom_member.entity.ChatRoomMember;
import piglin.swapswap.domain.chatroom_member.service.ChatRoomMemberServiceImpl;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.service.MemberServiceImplV1;
import piglin.swapswap.domain.message.dto.request.MessageRequestDto;
import piglin.swapswap.domain.message.service.MessageServiceImpl;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{

    private final ChatRoomRepository chatRoomRepository;
    private final MemberServiceImplV1 memberService;
    private final MessageServiceImpl messageService;
    private final ChatRoomMemberServiceImpl chatRoomMemberService;

    @Override
    @Transactional
    public List<ChatRoomResponseDto> getChatRoomList(Member member) {

        return chatRoomRepository.findAllByMemberIdWithMember(member.getId());
    }

    @Override
    public Long createChatroom(Member member, Long secondMemberId) {

        ChatRoom chatRoom = chatRoomRepository.findByMyMemberIdAndOpponentMemberId(member.getId(), secondMemberId)
                .orElseGet(() -> createChatRoomAndAddMember(member, secondMemberId));

        return chatRoom.getId();
    }

    @Override
    @Transactional
    public void leaveChatRoom(Member member, Long roomId) {

        ChatRoom chatRoom = findChatRoom(roomId);

        chatRoomMemberService.deleteChatRoomMember(chatRoom, member);

        deleteChatRoomAndMessagesIfNoParticipants(chatRoom);
    }

    @Override
    public ChatRoom findChatRoom(Long roomId) {

        return chatRoomRepository.findById(roomId).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_FOUND_CHATROOM_EXCEPTION));
    }

    @Override
    public ChatRoom findChatRoomByMessageDto(MessageRequestDto requestDto) {

        return chatRoomRepository.findById(requestDto.getChatRoomId()).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_FOUND_CHATROOM_EXCEPTION)
        );
    }

    private ChatRoom createChatRoomAndAddMember(Member member, Long secondMemberId) {

        Member secondMember = memberService.getMember(secondMemberId);

        ChatRoom chatRoom = chatRoomRepository.save(ChatRoomMapper.createChatRoom());
        chatRoomMemberService.addMemberToChatRoom(chatRoom, member, secondMember);

        return chatRoom;
    }

    private void deleteChatRoomAndMessagesIfNoParticipants(ChatRoom chatRoom) {

        List<ChatRoomMember> chatRoomMember = chatRoomMemberService.findAllByChatRoom(chatRoom);

        if (chatRoomMember.isEmpty()) {

            chatRoom.deleteChatRoom();
            messageService.messageIsDeletedToTrue(chatRoom);
        }
    }

}