package piglin.swapswap.domain.chatroom_member.repository;

import static piglin.swapswap.domain.chatroom_member.entity.QChatRoomMember.chatRoomMember;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.stereotype.Repository;
import piglin.swapswap.domain.chatroom_member.entity.ChatRoomMember;
import piglin.swapswap.domain.member.entity.Member;

@Repository
public class ChatRoomMemberQueryRepositoryImpl implements ChatRoomMemberQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public ChatRoomMemberQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ChatRoomMember> findAllByMemberWithChatRoom(Member member) {

        return queryFactory
                .selectFrom(chatRoomMember)
                .leftJoin(chatRoomMember.chatRoom).fetchJoin()
                .where(chatRoomMember.member.eq(member))
                .fetch();
    }

    @Override
    public void deleteAllChatroomByMember(Member loginMember) {
        queryFactory
                .update(chatRoomMember)
                .set(chatRoomMember.member.isDeleted, true)
                .where(chatRoomMember.member.eq(loginMember))
                .execute();

        em.flush();
        em.clear();
    }

    @Override
    public void reRegisterChatroomByMember(Member loginMember) {
        queryFactory
                .update(chatRoomMember)
                .set(chatRoomMember.member.isDeleted, false)
                .where(chatRoomMember.member.eq(loginMember))
                .execute();

        em.flush();
        em.clear();
    }
}
