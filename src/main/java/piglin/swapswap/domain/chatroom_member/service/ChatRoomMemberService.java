package piglin.swapswap.domain.chatroom_member.service;

import java.util.List;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.chatroom_member.entity.ChatRoomMember;
import piglin.swapswap.domain.member.entity.Member;

public interface ChatRoomMemberService {

    void deleteChatRoomMember(ChatRoom chatRoom, Member member);

    List<ChatRoomMember> findAllByChatRoom(ChatRoom chatRoom);

    void addMemberToChatRoom(ChatRoom chatRoom, Member member1, Member member2);

    ChatRoomMember findByChatRoomAndMember(ChatRoom chatRoom, Member member);
}
