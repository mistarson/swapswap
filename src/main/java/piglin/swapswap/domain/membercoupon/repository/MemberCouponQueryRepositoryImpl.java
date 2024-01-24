package piglin.swapswap.domain.membercoupon.repository;

import static piglin.swapswap.domain.membercoupon.entity.QMemberCoupon.memberCoupon;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import piglin.swapswap.domain.member.entity.Member;

public class MemberCouponQueryRepositoryImpl implements MemberCouponQueryRepository{
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public MemberCouponQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void deleteAllMemberCouponByMember(Member loginMember){
        queryFactory
                .update(memberCoupon)
                .set(memberCoupon.isDeleted, true)
                .where(memberCoupon.member.eq(loginMember))
                .execute();

        em.flush();
        em.clear();
    }

    @Override
    public void reRegisterCouponByMember(Member loginMember) {
        queryFactory
                .update(memberCoupon)
                .set(memberCoupon.isDeleted, false)
                .where(memberCoupon.member.eq(loginMember))
                .execute();

        em.flush();
        em.clear();
    }
}
