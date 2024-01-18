package piglin.swapswap.domain.deal.service;

import java.util.List;
import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.deal.dto.response.DealDetailResponseDto;
import piglin.swapswap.domain.deal.dto.response.DealGetResponseDto;
import piglin.swapswap.domain.member.entity.Member;

public interface DealService {

    Long createDeal(Member member, DealCreateRequestDto requestDto);

    List<DealGetResponseDto> getMyRequestDealList(Member member);

    List<DealGetResponseDto> getMyResponseDealList(Member member);

    DealDetailResponseDto getDeal(Long dealId, Member member);
}
