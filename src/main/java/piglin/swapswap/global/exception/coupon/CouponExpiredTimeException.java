package piglin.swapswap.global.exception.coupon;

import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

public class CouponExpiredTimeException extends BusinessException {

    public CouponExpiredTimeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
