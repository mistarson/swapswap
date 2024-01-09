package piglin.swapswap.domain.member.service;

import piglin.swapswap.domain.member.dto.MemberNicknameDto;
import piglin.swapswap.domain.member.entity.Member;

public interface MemberService {

    void updateNickname(Member member, MemberNicknameDto requestDto);

    void deleteMember(Member loginMember);

}
