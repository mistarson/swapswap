package piglin.swapswap.domain.coupon.service;

import piglin.swapswap.domain.coupon.dto.request.CouponCreateRequestDto;

public interface CouponService {

    Long createCoupon(CouponCreateRequestDto couponCreateRequestDto);

}
