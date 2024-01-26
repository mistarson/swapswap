package piglin.swapswap.domain.chatroom.repository;

import static piglin.swapswap.domain.chatroom.entity.QChatRoom.chatRoom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;

public class ChatRoomQueryRepositoryImpl implements ChatRoomQueryRepository{

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public ChatRoomQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ChatRoom> findAllByMemberId(Long memberId) {
        BooleanBuilder conditions = new BooleanBuilder();
        conditions.and(chatRoom.firstMemberId.eq(memberId).and(chatRoom.isLeaveFirstMember.isFalse()));
        conditions.or(chatRoom.secondMemberId.eq(memberId).and(chatRoom.isLeaveSecondMember.isFalse()));

        return queryFactory
                .selectFrom(chatRoom)
                .where(chatRoom.firstMemberId.eq(memberId).or(chatRoom.secondMemberId.eq(memberId)).and(conditions), chatRoom.isDeleted.isFalse())
                .fetch();
    }

    @Override
    public Optional<ChatRoom> findChatRoomByMemberIds(Long firstMemberId, Long secondMemberId) {
        BooleanBuilder conditions = new BooleanBuilder();

        conditions.andAnyOf(
                chatRoom.firstMemberId.eq(firstMemberId).and(chatRoom.secondMemberId.eq(secondMemberId)),
                chatRoom.firstMemberId.eq(secondMemberId).and(chatRoom.secondMemberId.eq(firstMemberId))
        );

        return Optional.ofNullable(
                queryFactory.selectFrom(chatRoom)
                        .where(conditions)
                        .where(chatRoom.isDeleted.eq(false))
                        .fetchOne()
        );
    }

}
