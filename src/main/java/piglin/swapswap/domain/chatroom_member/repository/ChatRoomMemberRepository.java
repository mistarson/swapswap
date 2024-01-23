package piglin.swapswap.domain.chatroom_member.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.chatroom_member.entity.ChatRoomMember;
import piglin.swapswap.domain.member.entity.Member;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long>, ChatRoomMemberQueryRepository {

    Optional<ChatRoomMember> findByChatRoomAndMember(ChatRoom chatRoom, Member member);
    List<ChatRoomMember> findAllByChatRoom(ChatRoom chatRoom);

}
