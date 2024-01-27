package piglin.swapswap.domain.billcoupon.repository;

import static piglin.swapswap.domain.billcoupon.entity.QBillCoupon.billCoupon;
import static piglin.swapswap.domain.membercoupon.entity.QMemberCoupon.memberCoupon;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.membercoupon.entity.MemberCoupon;

public class BillCouponQueryRepositoryImpl implements BillCouponQueryRepository {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public BillCouponQueryRepositoryImpl
            (EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<MemberCoupon> findMemberCouponFromBillCouponByBill(Bill bill) {

        return queryFactory
                .select(memberCoupon)
                .from(billCoupon)
                .where(billCoupon.bill.eq(bill))
                .fetch();
    }

}
