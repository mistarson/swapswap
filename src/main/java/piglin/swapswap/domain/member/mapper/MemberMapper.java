package piglin.swapswap.domain.member.mapper;

import piglin.swapswap.domain.member.constant.MemberRoleEnum;
import piglin.swapswap.domain.member.dto.SocialUserInfo;
import piglin.swapswap.domain.member.entity.Member;

public class MemberMapper {

    public static Member createMember(SocialUserInfo socialUserInfo) {

        return Member.builder()
                .email(socialUserInfo.email())
                .nickname(socialUserInfo.nickname())
                .role(MemberRoleEnum.USER)
                .isDeleted(false)
                // TODO wallet 넣어줘야 함 1:1이라 무조건 있어야 함
                .build();
    }

}
