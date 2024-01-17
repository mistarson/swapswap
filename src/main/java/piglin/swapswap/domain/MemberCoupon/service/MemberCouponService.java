package piglin.swapswap.domain.membercoupon.service;

import piglin.swapswap.domain.coupon.entity.Coupon;
import piglin.swapswap.domain.member.entity.Member;

public interface MemberCouponService {

    void saveMemberCoupon(Member member, Coupon coupon);

}
