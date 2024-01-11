package piglin.swapswap.domain.deal.service;

import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.post.dto.request.PostCreateRequestDto;

public interface DealService {
  Long createDeal(Long memberId, DealCreateRequestDto requestDto, Long secondMemberId);
}
