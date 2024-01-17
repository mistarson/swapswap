package piglin.swapswap.domain.deal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.deal.constant.DealStatus;
import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.deal.entity.Deal;
import piglin.swapswap.domain.deal.mapper.DealMapper;
import piglin.swapswap.domain.deal.repository.DealRepository;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.repository.MemberRepository;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

@Service
@RequiredArgsConstructor
public class DealServiceImplV1 implements DealService {


    private final DealRepository dealRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long createDeal(Member member, DealCreateRequestDto requestDto, Long secondMemberId) {

        getMember(secondMemberId);
        Member firstMember = getMember(member.getId());
        Deal deal = DealMapper.createDeal(requestDto, firstMember, secondMemberId);

        dealRepository.save(deal);
        return deal.getId();
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
    }

    private DealStatus allowDealBoth(Boolean firstAllow, Boolean secondAllow) {

        if (firstAllow && secondAllow) {

            return DealStatus.DEALING;
        } else {

            return DealStatus.REQUESTED;
        }
    }
}

