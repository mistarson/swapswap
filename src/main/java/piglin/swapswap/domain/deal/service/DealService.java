package piglin.swapswap.domain.deal.service;

import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.member.entity.Member;

public interface DealService {

    Long createDeal(Member member, DealCreateRequestDto requestDto);

}
