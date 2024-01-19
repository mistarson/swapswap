package piglin.swapswap.domain.deal.service;

import java.util.List;
import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.deal.dto.response.DealDetailResponseDto;
import piglin.swapswap.domain.deal.dto.response.DealGetResponseDto;
import piglin.swapswap.domain.member.entity.Member;

public interface DealService {

    Long createDeal(Member member, DealCreateRequestDto requestDto);

    List<DealGetResponseDto> getMyRequestDealList(Long memberId);

    List<DealGetResponseDto> getMyResponseDealList(Long memberId);

    DealDetailResponseDto getDeal(Long dealId, Member member);
}
