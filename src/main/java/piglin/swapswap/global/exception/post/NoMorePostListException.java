package piglin.swapswap.global.exception.post;

import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

public class NoMorePostListException extends BusinessException {

    public NoMorePostListException() {
        super(ErrorCode.NO_MORE_POST_LIST);
    }
}
