package piglin.swapswap.domain.coupon.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import piglin.swapswap.domain.coupon.constant.CouponType;

public record CouponCreateRequestDto(@NotBlank @Length(max = 60) String couponName,
                                     CouponType couponType,
                                     @Min(1) @Max(100) int discountPercentage,
                                     @DateTimeFormat LocalDateTime expiredTime,
                                     @Min(1) int couponCount) {

}
