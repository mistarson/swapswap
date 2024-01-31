package piglin.swapswap.domain.deal.service;

import java.util.List;
import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.deal.dto.response.DealDetailResponseDto;
import piglin.swapswap.domain.deal.dto.response.DealGetReceiveDto;
import piglin.swapswap.domain.deal.dto.response.DealGetRequestDto;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.membercoupon.dto.response.MyCouponGetResponseDto;

public interface DealService {

    Long createDeal(Member member, DealCreateRequestDto requestDto);

    List<DealGetRequestDto> getMyRequestDealList(Long memberId);

    List<DealGetReceiveDto> getMyReceiveDealList(Long memberId);

    DealDetailResponseDto getDeal(Long dealId, Member member);

    void updateDealAllowWithoutSwapPay(Long dealId, Member member);

    void updateDealAllowTrueWithSwapPay(Long dealId, Member member);

    void updateDealAllowFalseWithSwapPay(Long dealId, Member member);
}
