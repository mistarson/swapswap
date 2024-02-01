package piglin.swapswap.global.exception.ajax;

import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

public class AjaxRequestException extends BusinessException {

    public AjaxRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
