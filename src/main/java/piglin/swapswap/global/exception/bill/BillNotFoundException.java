package piglin.swapswap.global.exception.bill;

import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

public class BillNotFoundException extends BusinessException {

    public BillNotFoundException(ErrorCode errorCode) {super(errorCode);}
}
