package piglin.swapswap.domain.chatroom_member.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.chatroom_member.entity.ChatRoomMember;
import piglin.swapswap.domain.chatroom_member.mapper.ChatRoomMemberMapper;
import piglin.swapswap.domain.chatroom_member.repository.ChatRoomMemberRepository;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

@Service
@RequiredArgsConstructor
public class ChatRoomMemberServiceImpl implements ChatRoomMemberService {

    private final ChatRoomMemberRepository chatRoomMemberRepository;

    @Override
    public void deleteChatRoomMember(ChatRoom chatRoom, Member member) {

        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findByChatRoomAndMember(chatRoom, member).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_CHAT_ROOM_MEMBER_EXCEPTION));

        chatRoomMemberRepository.delete(chatRoomMember);
    }

    @Override
    public List<ChatRoomMember> findAllByChatRoom(ChatRoom chatRoom) {

        return chatRoomMemberRepository.findAllByChatRoom(chatRoom);
    }

    @Override
    public void addMemberToChatRoom(ChatRoom chatRoom, Member member1, Member member2) {

        chatRoomMemberRepository.save(ChatRoomMemberMapper.createChatRoomMember(chatRoom, member1));
        chatRoomMemberRepository.save(ChatRoomMemberMapper.createChatRoomMember(chatRoom, member2));
    }

    @Override
    public ChatRoomMember findByChatRoomAndMember(ChatRoom chatRoom, Member member) {

        return chatRoomMemberRepository.findByChatRoomAndMember(chatRoom, member).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_CHAT_ROOM_MEMBER_EXCEPTION));
    }

    @Override
    public void deleteAllChatroomByMember(Member loginMember) {
        chatRoomMemberRepository.deleteAllChatroomByMember(loginMember);
    }

    @Override
    public void reRegisterChatroomByMember(Member loginMember) {
        chatRoomMemberRepository.reRegisterChatroomByMember(loginMember);
    }
}
