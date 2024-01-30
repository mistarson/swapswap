package piglin.swapswap.domain.chatroom.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomQueryRepository{

    Optional<ChatRoom> findByIdAndIsDeletedFalse(Long roomId);

    void deleteAllByIsDeletedIsTrueAndModifiedTimeBefore(LocalDateTime fourteenDaysAgo);
}