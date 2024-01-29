package piglin.swapswap.domain.chatroom.repository;

import static piglin.swapswap.domain.chatroom.entity.QChatRoom.chatRoom;
import static piglin.swapswap.domain.member.entity.QMember.*;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import piglin.swapswap.domain.chatroom.dto.ChatRoomResponseDto;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.member.entity.QMember;

public class ChatRoomQueryRepositoryImpl implements ChatRoomQueryRepository{

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public ChatRoomQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ChatRoom> findAllByMemberId(Long memberId) {

        return queryFactory
                .selectFrom(chatRoom)
                .where(findChatRoomEq(memberId), chatRoom.isDeleted.isFalse())
                .fetch();
    }

    @Override
    public Optional<ChatRoom> findChatRoomByMemberIds(Long firstMemberId, Long secondMemberId) {

        return Optional.ofNullable(
                queryFactory.selectFrom(chatRoom)
                        .where(chatRoomFirstMemberAndSecondMemberEq(firstMemberId, secondMemberId).and(chatRoom.isDeleted.eq(false)))
                        .fetchOne()
        );
    }

    private BooleanBuilder findChatRoomEq(Long memberId) {

        return new BooleanBuilder().and(chatRoom.firstMemberId.eq(memberId).and(chatRoom.isLeaveFirstMember.isFalse()))
                                   .or(chatRoom.secondMemberId.eq(memberId).and(chatRoom.isLeaveSecondMember.isFalse()));
    }

    private BooleanBuilder chatRoomFirstMemberAndSecondMemberEq(Long firstMemberId, Long secondMemberId) {

        return new BooleanBuilder().andAnyOf(
                chatRoom.firstMemberId.eq(firstMemberId).and(chatRoom.secondMemberId.eq(secondMemberId)),
                chatRoom.firstMemberId.eq(secondMemberId).and(chatRoom.secondMemberId.eq(firstMemberId))
        );
    }
}
