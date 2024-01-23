package piglin.swapswap.domain.member.dto;

import jakarta.validation.constraints.Pattern;

public record MemberNicknameDto(

        @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,15}$", message = "한글, 알파벳 대,소문자, 숫자로 구성해주세요")
        String nickname) {

}
