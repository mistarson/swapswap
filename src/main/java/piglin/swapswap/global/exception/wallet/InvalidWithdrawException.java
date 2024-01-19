package piglin.swapswap.global.exception.wallet;

import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

public class InvalidWithdrawException extends BusinessException {

    public InvalidWithdrawException(ErrorCode errorCode) {
        super(errorCode);
    }
}
