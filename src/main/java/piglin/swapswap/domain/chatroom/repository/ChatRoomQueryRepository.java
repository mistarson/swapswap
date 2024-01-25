package piglin.swapswap.domain.chatroom.repository;

import java.util.List;
import java.util.Optional;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.member.entity.Member;

public interface ChatRoomQueryRepository {

    List<ChatRoom> findAllByMember(Member member);

    Optional<ChatRoom> findChatRoomByMembers(Member member1, Member member2);

}
