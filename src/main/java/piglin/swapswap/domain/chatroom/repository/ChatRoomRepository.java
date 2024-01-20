package piglin.swapswap.domain.chatroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String>, ChatRoomQueryRepository{

}