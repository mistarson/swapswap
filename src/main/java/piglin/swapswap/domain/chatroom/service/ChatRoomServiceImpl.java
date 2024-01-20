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
import piglin.swapswap.domain.member.repository.MemberRepository;
import piglin.swapswap.domain.message.repository.MessageRepository;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{

    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;

    @Override
    @Transactional
    public List<ChatRoomResponseDto> getChatRoomList(Member member) {

        List<ChatRoomMember> chatRoomMemberList =  chatRoomMemberRepository.findAllByMemberWithChatRoom(member);

        return ChatRoomMapper.getChatRoomFromChatRoomMemberList(chatRoomMemberList);
    }

    @Override
    public String createChatroom(Member member, Long secondMemberId) {

        if (member.getId().equals(secondMemberId)) {
            throw new BusinessException(ErrorCode.CHAT_ONLY_DIFFERENT_USER_EXCEPTION);
        }

        Member secondMember = findMember(secondMemberId);

        List<ChatRoomMember> chatRoomMemberList1 = chatRoomMemberRepository.findAllByMember(member);
        List<ChatRoomMember> chatRoomMemberList2 = chatRoomMemberRepository.findAllByMember(secondMember);

        for (ChatRoomMember chatRoomMember1 : chatRoomMemberList1) {
            for (ChatRoomMember chatRoomMember2 : chatRoomMemberList2) {
                if (chatRoomMember1.getChatRoom().getId().equals(chatRoomMember2.getChatRoom().getId())) {
                    return chatRoomMember1.getChatRoom().getId();
                }
            }
        }

        return createChatRoomAndAddMember(member, secondMember);
    }

    @Override
    @Transactional
    public void leaveChatRoom(Member member, String roomId) {

        ChatRoom chatRoom = findChatRoom(roomId);

        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findByChatRoomAndMember(chatRoom, member).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_CHAT_ROOM_MEMBER_EXCEPTION));

        chatRoomMemberRepository.delete(chatRoomMember);

        deleteChatRoomAndMessagesIfNoParticipants(chatRoom);
    }

    private ChatRoom findChatRoom(String roomId) {

        return chatRoomRepository.findById(roomId).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_FOUND_CHATROOM_EXCEPTION));
    }

    private Member findMember(Long memberId) {

        return memberRepository.findById(memberId).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
    }

    private String createChatRoomAndAddMember(Member member, Member secondMember) {

        ChatRoom chatRoom = chatRoomRepository.save(ChatRoomMapper.createChatRoom());
        chatRoomMemberRepository.save(ChatRoomMemberMapper.createChatRoomMember(chatRoom, member));
        chatRoomMemberRepository.save(ChatRoomMemberMapper.createChatRoomMember(chatRoom, secondMember));

        return chatRoom.getId();
    }

    private void deleteChatRoomAndMessagesIfNoParticipants(ChatRoom chatRoom) {

        List<ChatRoomMember> chatRoomMember = chatRoomMemberRepository.findAllByChatRoom(chatRoom);

        if (chatRoomMember.isEmpty()) {

            chatRoom.setIsDeleted(true);
            messageRepository.messageIsDeletedToTrue(chatRoom);
        }
    }

}