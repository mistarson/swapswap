package piglin.swapswap.domain.member.mapper;

import piglin.swapswap.domain.member.constant.MemberRole;
import piglin.swapswap.domain.member.dto.SocialUserInfo;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.wallet.entity.Wallet;

public class MemberMapper {

    public static Member createMember(SocialUserInfo socialUserInfo, Wallet wallet) {

        return Member.builder()
                .email(socialUserInfo.email())
                .nickname(socialUserInfo.nickname())
                .role(MemberRole.USER)
                .isDeleted(false)
                .wallet(wallet)
                .build();
    }
}
