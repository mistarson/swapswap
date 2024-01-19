package piglin.swapswap.domain.coupon.service;

import piglin.swapswap.domain.coupon.dto.request.CouponCreateRequestDto;
import piglin.swapswap.domain.coupon.dto.response.CouponGetResponseDto;
import piglin.swapswap.domain.member.entity.Member;

public interface CouponService {

    Long createCoupon(CouponCreateRequestDto couponCreateRequestDto);

    int getCouponCount(Long couponId);

    void issueEventCouponByPessimisticLock(Long couponId, Member member);

    void issueEventCouponByOptimisticLock(Long couponId, Member member);

    CouponGetResponseDto getCouponDetail(Long couponId);

}
