package piglin.swapswap.domain.member.dto;

import lombok.Builder;

@Builder
public record MemberResponseDto(
        Long id,
        String nickname,
        String email
) {

}