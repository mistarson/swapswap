package piglin.swapswap.domain.member.service;

import java.util.List;
import piglin.swapswap.domain.member.dto.MemberNicknameDto;
import piglin.swapswap.domain.member.entity.Member;

public interface MemberService {

    void updateNickname(Member member, MemberNicknameDto requestDto);

    void deleteMember(Member loginMember);

    Long getMySwapMoney(Member member);

    Member getMemberWithWallet(Long memberId);

    Member getMember(Long memberId);

    boolean checkNicknameExists(String nickname);

    List<Member> getMembers(List<Long> memberIds);
}
