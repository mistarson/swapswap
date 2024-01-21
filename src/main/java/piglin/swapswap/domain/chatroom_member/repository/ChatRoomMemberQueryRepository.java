package piglin.swapswap.domain.chatroom_member.repository;

import java.util.List;
import piglin.swapswap.domain.chatroom_member.entity.ChatRoomMember;
import piglin.swapswap.domain.member.entity.Member;

public interface ChatRoomMemberQueryRepository {

    List<ChatRoomMember> findAllByMemberWithChatRoom(Member member);
}
