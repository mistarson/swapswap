package piglin.swapswap.domain.chatroom.repository;

import static piglin.swapswap.domain.chatroom.entity.QChatRoom.chatRoom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.member.entity.Member;

public class ChatRoomQueryRepositoryImpl implements ChatRoomQueryRepository{

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public ChatRoomQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ChatRoom> findAllByMember(Member member) {

        return queryFactory.selectFrom(chatRoom)
                .where(chatRoom.member1.eq(member).or(chatRoom.member2.eq(member)))
                .fetch();
    }

    @Override
    public Optional<ChatRoom> findChatRoomByMembers(Member member1, Member member2) {

        return Optional.ofNullable(queryFactory
                .selectFrom(chatRoom)
                .where(
                        chatRoom.member1.eq(member1).and(chatRoom.member2.eq(member2))
                                .or(chatRoom.member1.eq(member2).and(chatRoom.member2.eq(member1)))
                )
                .fetchOne());
    }
}
