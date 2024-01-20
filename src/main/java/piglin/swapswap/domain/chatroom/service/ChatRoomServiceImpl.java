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
import piglin.swapswap.domain.chatroom_member.mapper.ChatRoomMemberMapper;
import piglin.swapswap.domain.chatroom_member.repository.ChatRoomMemberRepository;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.service.MemberServiceImplV1;
import piglin.swapswap.domain.message.repository.MessageRepository;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{

    private final MemberServiceImplV1 memberService;
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;

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

        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findByChatRoomAndMember(chatRoom, member).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_CHAT_ROOM_MEMBER_EXCEPTION));

        chatRoomMemberRepository.delete(chatRoomMember);

        deleteChatRoomAndMessagesIfNoParticipants(chatRoom);
    }

    private ChatRoom findChatRoom(Long roomId) {

        return chatRoomRepository.findById(roomId).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_FOUND_CHATROOM_EXCEPTION));
    }

    private ChatRoom createChatRoomAndAddMember(Member member, Long secondMemberId) {

        Member secondMember = memberService.getMember(secondMemberId);

        ChatRoom chatRoom = chatRoomRepository.save(ChatRoomMapper.createChatRoom());
        chatRoomMemberRepository.save(ChatRoomMemberMapper.createChatRoomMember(chatRoom, member));
        chatRoomMemberRepository.save(ChatRoomMemberMapper.createChatRoomMember(chatRoom, secondMember));

        return chatRoom;
    }

    private void deleteChatRoomAndMessagesIfNoParticipants(ChatRoom chatRoom) {

        List<ChatRoomMember> chatRoomMember = chatRoomMemberRepository.findAllByChatRoom(chatRoom);

        if (chatRoomMember.isEmpty()) {

            chatRoom.deleteChatRoom();
            messageRepository.messageIsDeletedToTrue(chatRoom);
        }
    }

}