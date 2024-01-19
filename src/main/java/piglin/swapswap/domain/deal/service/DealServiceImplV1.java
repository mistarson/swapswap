package piglin.swapswap.domain.deal.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.deal.constant.DealStatus;
import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.deal.dto.response.DealGetResponseDto;
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
    public Long createDeal(Member member, DealCreateRequestDto requestDto) {

        existMember(requestDto.secondMemberId());

        Member firstMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
        Deal deal = DealMapper.createDeal(requestDto, firstMember.getId());

        Deal savedDeal = dealRepository.save(deal);

        return savedDeal.getId();
    }

    @Override
    public List<DealGetResponseDto> getMyRequestDealList(Long meberId) {

        return dealRepository.findAllMyDealRequest(meberId);
    }

    @Override
    public List<DealGetResponseDto> getMyResponseDealList(Long meberId) {

        return dealRepository.findAllMyDealResponse(meberId);
    }

    private DealStatus allowDealBoth(Boolean firstAllow, Boolean secondAllow) {

        if (firstAllow && secondAllow) {

            return DealStatus.DEALING;
        } else {

            return DealStatus.REQUESTED;
        }
    }

    private void existMember(Long memberId) {

        if (!memberRepository.existsByIdAndIsDeletedIsFalse(memberId)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_POST_EXCEPTION);
        }
    }


}

