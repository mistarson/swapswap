package piglin.swapswap.domain.member.dto;

import lombok.Builder;

@Builder
public record SocialUserInfo(
        Long id,
        String nickname,
        String email
) {

    public static SocialUserInfo createSocialUserInfo(Long id, String nickname, String email) {
        return SocialUserInfo.builder()
                .id(id)
                .nickname(nickname)
                .email(email)
                .build();
    }

}