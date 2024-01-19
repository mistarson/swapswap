package piglin.swapswap.global.exception.jwt;

import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

public class JwtInvalidException extends BusinessException {

    public JwtInvalidException() {
        super(ErrorCode.INVALID_JWT_EXCEPTION);
    }
}
