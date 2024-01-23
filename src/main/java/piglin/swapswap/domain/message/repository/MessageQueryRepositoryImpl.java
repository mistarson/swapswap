package piglin.swapswap.domain.message.repository;

import static piglin.swapswap.domain.message.entity.QMessage.message;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;

@Repository
public class MessageQueryRepositoryImpl implements MessageQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public MessageQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void deleteMessage(ChatRoom chatRoom) {

        queryFactory.update(message)
                .set(message.isDeleted, true)
                .where(message.chatRoom.id.eq(chatRoom.getId()))
                .execute();
    }

}