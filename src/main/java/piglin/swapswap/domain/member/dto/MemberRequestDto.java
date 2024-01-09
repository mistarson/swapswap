package piglin.swapswap.domain.member.dto;

import jakarta.validation.constraints.Pattern;

public record MemberRequestDto(
        @Pattern(regexp = "^[a-zA-Z0-9/가-힣]{2,20}$", message = "한글, 알파벳 대,소문자, 숫자로 구성해주세요")
        String nickname,

        @Pattern(regexp = "^([a-z0-9]+)@([\\da-z\\.-]+)\\.([a-z\\.]{1,40})$", message = "이메일 형식으로 작성해주세요")
        String email,

        String role) {

}
