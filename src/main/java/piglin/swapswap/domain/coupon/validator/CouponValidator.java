package piglin.swapswap.domain.coupon.validator;

import java.time.LocalDateTime;
import piglin.swapswap.global.exception.common.ErrorCode;
import piglin.swapswap.global.exception.coupon.CouponExpiredTimeException;

public class CouponValidator {

    public static void validateExpiredTime(LocalDateTime expiredTime) {

        if (expiredTime.isBefore(LocalDateTime.now())) {
            throw new CouponExpiredTimeException(ErrorCode.INVALID_EXPIRED_TIME_EXCEPTION);
        }
    }

}
