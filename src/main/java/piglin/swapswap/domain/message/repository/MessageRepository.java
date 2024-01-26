package piglin.swapswap.domain.message.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import piglin.swapswap.domain.message.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long>, MessageQueryRepository {

    List<Message> findAllByChatRoomId(Long roomId);
}
