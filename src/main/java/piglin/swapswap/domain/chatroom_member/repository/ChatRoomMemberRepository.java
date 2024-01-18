package piglin.swapswap.domain.chatroom_member.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.chatroom_member.entity.ChatRoomMember;
import piglin.swapswap.domain.member.entity.Member;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {

    List<ChatRoomMember> findAllByMember(Member member);

    List<ChatRoomMember> findAllByChatRoom(ChatRoom chatRoom);
}
