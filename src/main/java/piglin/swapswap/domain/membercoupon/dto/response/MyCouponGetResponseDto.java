package piglin.swapswap.domain.membercoupon.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import piglin.swapswap.domain.coupon.constant.CouponType;

@Builder
public record MyCouponGetResponseDto(
        String couponName,
        CouponType couponType,
        int discountPercentage,
        LocalDateTime expiredTime
) {

}
