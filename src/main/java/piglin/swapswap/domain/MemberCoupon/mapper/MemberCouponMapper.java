package piglin.swapswap.domain.membercoupon.mapper;

import java.util.List;
import piglin.swapswap.domain.coupon.entity.Coupon;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.membercoupon.dto.response.MyCouponGetResponseDto;
import piglin.swapswap.domain.membercoupon.entity.MemberCoupon;

public class MemberCouponMapper {

    public static MemberCoupon createMemberCoupon(Member member, Coupon coupon) {

        return MemberCoupon.builder()
                .name(coupon.getName())
                .couponType(coupon.getCouponType())
                .discountPercentage(coupon.getDiscountPercentage())
                .expiredTime(coupon.getExpiredTime())
                .member(member)
                .build();
    }

    public static List<MyCouponGetResponseDto> memberCouponListToMyCouponResponseDtoList(
            List<MemberCoupon> memberCouponList) {

        return memberCouponList.stream().map(memberCoupon ->
                MyCouponGetResponseDto.builder()
                .couponName(memberCoupon.getName())
                .couponType(memberCoupon.getCouponType())
                .discountPercentage(memberCoupon.getDiscountPercentage())
                .expiredTime(memberCoupon.getExpiredTime())
                .build()).toList();
    }

}
