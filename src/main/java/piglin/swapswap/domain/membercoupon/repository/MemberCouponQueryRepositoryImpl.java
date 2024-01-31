package piglin.swapswap.domain.membercoupon.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import piglin.swapswap.domain.member.entity.QMember;
import piglin.swapswap.domain.membercoupon.entity.MemberCoupon;
import piglin.swapswap.domain.membercoupon.entity.QMemberCoupon;

public class MemberCouponQueryRepositoryImpl implements MemberCouponQueryRepository {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public MemberCouponQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<MemberCoupon> findByIdAndIsUserIsFalseWithMember(Long memberCouponId) {

        return Optional.ofNullable(queryFactory
                .selectFrom(QMemberCoupon.memberCoupon)
                .join(QMemberCoupon.memberCoupon.member, QMember.member).fetchJoin()
                .where(memberCouponIdEq(memberCouponId))
                .fetchOne());
    }

    private BooleanExpression memberCouponIdEq(Long memberCouponId) {

        return QMemberCoupon.memberCoupon.id.eq(memberCouponId);
    }

}
