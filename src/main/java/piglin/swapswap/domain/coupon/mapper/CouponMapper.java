package piglin.swapswap.domain.coupon.mapper;

import piglin.swapswap.domain.coupon.dto.request.CouponCreateRequestDto;
import piglin.swapswap.domain.coupon.dto.response.CouponGetResponseDto;
import piglin.swapswap.domain.coupon.entity.Coupon;

public class CouponMapper {

    public static Coupon createCoupon(CouponCreateRequestDto couponCreateRequestDto) {

        return Coupon.builder()
                .name(couponCreateRequestDto.couponName())
                .couponType(couponCreateRequestDto.couponType())
                .discountPercentage(couponCreateRequestDto.discountPercentage())
                .expiredTime(couponCreateRequestDto.expiredTime())
                .count(couponCreateRequestDto.couponCount())
                .build();
    }

    public static CouponGetResponseDto couponToGetResponseDto(Coupon coupon) {

        return CouponGetResponseDto.builder()
                .couponId(coupon.getId())
                .couponName(coupon.getName())
                .couponType(coupon.getCouponType())
                .discountPercentage(coupon.getDiscountPercentage())
                .expiredTime(coupon.getExpiredTime())
                .couponCount(coupon.getCount())
                .build();
    }

}
