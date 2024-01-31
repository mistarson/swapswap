package piglin.swapswap.domain.billcoupon.repository;

import java.util.List;
import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.billcoupon.entity.BillCoupon;
import piglin.swapswap.domain.membercoupon.entity.MemberCoupon;

public interface BillCouponQueryRepository {

    List<MemberCoupon> findMemberCouponFromBillCouponByBill(Bill bill);

    List<BillCoupon> findByBillWithMemberCoupon(Bill bill);

    void deleteAllByBill(List<BillCoupon> billCouponList);
}
