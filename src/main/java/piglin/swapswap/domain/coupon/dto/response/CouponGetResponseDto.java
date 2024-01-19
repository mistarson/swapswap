package piglin.swapswap.domain.coupon.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import piglin.swapswap.domain.coupon.constant.CouponType;

@Builder
public record CouponGetResponseDto(
        Long couponId,
        String couponName,
        CouponType couponType,
        int discountPercentage,
        LocalDateTime expiredTime,
        int couponCount) {

}
