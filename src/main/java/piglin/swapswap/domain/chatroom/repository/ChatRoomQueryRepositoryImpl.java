package piglin.swapswap.domain.chatroom.repository;

import static piglin.swapswap.domain.chatroom.entity.QChatRoom.chatRoom;
import static piglin.swapswap.domain.chatroom_member.entity.QChatRoomMember.chatRoomMember;
import static piglin.swapswap.domain.member.entity.QMember.member;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import piglin.swapswap.domain.chatroom.dto.ChatRoomResponseDto;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;

public class ChatRoomQueryRepositoryImpl implements ChatRoomQueryRepository{

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public ChatRoomQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ChatRoomResponseDto> findAllByMemberIdWithMember(Long memberId){
        return queryFactory
                .select(Projections.constructor(ChatRoomResponseDto.class,
                        chatRoom.id,
                        member.nickname,
                        chatRoom.lastMessage,
                        chatRoom.lastMessageTime))
                .from(chatRoomMember)
                .join(chatRoom).on(chatRoomMember.chatRoom.id.eq(chatRoom.id))
                .join(member).on(chatRoomMember.member.id.eq(member.id))
                .where(memberIdNotEq(memberId))
                .fetch();
    }

    @Override
    public Optional<ChatRoom> findByMyMemberIdAndOpponentMemberId(Long memberId, Long secondMemberId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(chatRoom)
                .join(chatRoomMember).on(chatRoom.id.eq(chatRoomMember.chatRoom.id))
                .where(memberIdEq(memberId), chatRoom.id.in(JPAExpressions
                        .select(chatRoomMember.chatRoom.id)
                                .from(chatRoomMember)
                        .where(memberIdEq(secondMemberId))))
                .fetchFirst()
        );
    }

    private BooleanExpression memberIdNotEq(Long memberId) {

        return member.id.ne(memberId);
    }

    private BooleanExpression memberIdEq(Long memberId) {

        return chatRoomMember.member.id.eq(memberId);
    }

}
