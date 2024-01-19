package piglin.swapswap.global.exception.common;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // JWT
    INVALID_JWT_SIGNATURE_EXCEPTION(401, "잘못된 JWT 서명입니다."),
    EXPIRED_JWT_TOKEN_EXCEPTION(401, "만료된 JWT 토큰입니다."),
    UNSUPPORTED_JWT_TOKEN_EXCEPTION(401, "지원되지 않는 JWT 토큰입니다."),
    INVALID_JWT_EXCEPTION(401, "JWT 토큰이 잘못되었습니다"),
    NOT_REFRESH_TOKEN_EXCEPTION(401, "Refresh Token이 아닙니다."),
    NOT_MISMATCHED_REFRESH_TOKEN_EXCEPTION(401, "DB의 리프레쉬 토큰 값과 다릅니다."),
    NO_JWT_EXCEPTION(401, "이 요청은 JWT가 필요합니다."),
    NOT_SUPPORTED_GRANT_TYPE_EXCEPTION(401, "지원하지 않는 권한 부여 유형입니다."),

    // 회원
    NOT_FOUND_USER_EXCEPTION(401, "회원 정보를 찾을 수 없습니다."),
    FAILED_AUTHENTICATION_EXCEPTION(401, "인증에 실패하였습니다."),
    ALREADY_EXIST_USER_NAME_EXCEPTION(409, "이미 존재하는 이름입니다."),
    ALREADY_EXIST_EMAIL_EXCEPTION(409, "이미 존재하는 이메일입니다."),
    UNAUTHORIZED_MODIFY_EXCEPTION(401, "수정할 권한이 없습니다."),
    NO_AUTHORIZATION_EXCEPTION(400, "접근 권한이 없습니다"),
    MISMATCHED_PASSWORD_EXCEPTION(401, "비밀번호가 일치하지 않습니다."),
    FAILED_LOGIN_EXCEPTION(401, "닉네임 또는 패스워드를 확인해주세요."),

    // 이메일 인증
    MISMATCHED_AUTH_CODE_EXCEPTION(401, "인증번호가 일치하지 않습니다."),
    NOT_FOUND_AUTH_CODE_EXCEPTION(401, "없는 인증 번호입니다."),

    // Comment
    REJECT_MODIFIYING_POST_EXCEPTION(403, "작성자만 수정할 수 있습니다."),
    NOT_FOUND_COMMENT_EXCEPTION(401, "해당 댓글을 찾을 수 없습니다."),

    // 게시글
    NOT_FOUND_POST_EXCEPTION(401, "게시글을 찾을 수 없습니다."),
    WRITE_ONLY_USER(401, "로그인 한 유저만 게시글을 작성할 수 있습니다"),
    UP_IS_NEED_ONE_DAY(400, "게시된지 하루, 업 한지 하루 이상 된 게시글만 업 할 수 있습니다."),
    POST_ALREADY_DELETED(400, "이미 지워진 게시글입니다"),

    // 쿠폰
    INVALID_EXPIRED_TIME_EXCEPTION(401, "만료 시간은 현재 시간보다 이전 시간일 수 없습니다."),
    NOT_FOUND_COUPON_EXCEPTION(401, "쿠폰을 찾을 수 없습니다."),
    INVALID_COUPON_EXCEPTION(401, "이미 모두 소진된 쿠폰입니다."),

    //거래
    NOT_FOUND_DEAL_EXCEPTION(401, "거래를 찾을 수 없습니다."),
    REQUEST_ONLY_DIFFERENT_USER_EXCEPTION(401, "자신의 게시물에는 거래요청을 할 수 없습니다."),

    // 지갑
    LACK_OF_SWAP_MONEY_EXCEPTION(400, "내 지갑의 스왑머니가 부족하여 출금할 수 없습니다."),

    // S3
    IS_NOT_IMAGE(400, "이미지 파일만 업로드 할 수 있습니다."),
    FAIL_UPLOAD(400, "이미지 업로드에 실패했습니다."),
    POST_IMAGE_MAX_SIZE(400, "이미지는 최대 10장만 업로드 할 수 있습니다."),
    POST_IMAGE_MIN_SIZE(400, "이미지를 최소 1장 업로드 해야합니다.");


    private final int status;

    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
