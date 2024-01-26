package piglin.swapswap.domain.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.member.dto.MemberNicknameDto;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.repository.MemberRepository;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

@Service
@RequiredArgsConstructor
public class MemberServiceImplV1 implements MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void updateNickname(Member member, MemberNicknameDto requestDto) {

        member = memberRepository.findById(member.getId()).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION)
        );

        if (memberRepository.existsByNicknameAndIsDeletedIsFalse(requestDto.nickname())) {
            throw new BusinessException(ErrorCode.ALREADY_EXIST_USER_NAME_EXCEPTION);
        }

        member.updateMember(requestDto.nickname());
    }


    @Transactional
    public void deleteMember(Member loginMember) {

        Member member = memberRepository.findByIdAndIsDeletedIsFalse(loginMember.getId())
                .orElseThrow(
                        () -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION)
                );

        member.deleteMember();
    }

    @Override
    @Transactional
    public Long getMySwapMoney(Long memberId) {

        Member member = getMemberWithWallet(memberId);

        return member.getWallet().getSwapMoney();
    }

    @Override
    public Member getMemberWithWallet(Long memberId) {
        return memberRepository.findByIdWithWallet(memberId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
    }

    @Override
    public Member getMember(Long memberId) {

        return memberRepository.findByIdAndIsDeletedIsFalse(memberId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
    }

    @Override
    public boolean checkNicknameExists(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }
}