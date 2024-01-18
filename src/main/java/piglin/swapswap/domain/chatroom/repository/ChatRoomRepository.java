package piglin.swapswap.domain.chatroom.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.chatroom_member.entity.ChatRoomMember;
import piglin.swapswap.domain.member.entity.Member;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {

    List<ChatRoom> findAllByChatRoomMembersContaining(ChatRoomMember chatRoomMember);
}