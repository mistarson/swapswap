package piglin.swapswap.domain.deal.service;

import java.util.List;
import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.deal.dto.request.DealUpdateRequestDto;
import piglin.swapswap.domain.deal.dto.response.DealDetailResponseDto;
import piglin.swapswap.domain.deal.dto.response.DealGetResponseDto;
import piglin.swapswap.domain.deal.dto.response.DealHistoryResponseDto;
import piglin.swapswap.domain.member.entity.Member;

public interface DealService {

    Long createDeal(Member member, DealCreateRequestDto requestDto);

    List<DealGetResponseDto> getMyRequestDealList(Long memberId);

    List<DealGetResponseDto> getMyResponseDealList(Long memberId);

    DealDetailResponseDto getDeal(Long dealId, Member member);

    void updateDeal(Member member, Long dealId, Long memberId, DealUpdateRequestDto requestDto);

    void checkDeal(Long dealId);

    void updateDealAllow(Long dealId, Member member);

    void takeDeal(Long deal, Member member);

    void updateDealSwapMoneyIsUsing(Long dealId, Member member);

    List<DealHistoryResponseDto> getDealHistoryList(Long memberId);
}
