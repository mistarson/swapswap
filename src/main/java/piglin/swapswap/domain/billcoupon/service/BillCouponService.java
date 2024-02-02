package piglin.swapswap.domain.billcoupon.service;

import java.util.List;
import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.billcoupon.dto.BillCouponResponseDto;
import piglin.swapswap.domain.billcoupon.dto.RedeemCouponRequestDto;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.membercoupon.entity.MemberCoupon;

public interface BillCouponService {

    List<BillCouponResponseDto>  getBillCouponDtoList(Bill bill);

    Long createBillCoupon(Bill bill, MemberCoupon memberCoupon);

    boolean existsBillCoupon(Bill bill);

    void deleteBillCouponByBillId(Long billId);

    void redeemCoupons(Long billId, Member member, RedeemCouponRequestDto dealRedeemCouponRequestDto);

    void initialBillCouponList(Long billId);
}
