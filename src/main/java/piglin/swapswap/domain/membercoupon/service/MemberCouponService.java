package piglin.swapswap.domain.membercoupon.service;

import java.util.List;
import piglin.swapswap.domain.coupon.entity.Coupon;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.membercoupon.dto.response.MyCouponGetResponseDto;

public interface MemberCouponService {

    void saveMemberCoupon(Member member, Coupon coupon);

    List<MyCouponGetResponseDto> getMycouponList(Member member);
}
