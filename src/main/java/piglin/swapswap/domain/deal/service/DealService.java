package piglin.swapswap.domain.deal.service;

import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.dto.request.PostCreateRequestDto;

public interface DealService {
  Long createDeal(Member member, DealCreateRequestDto requestDto, Long secondMemberId);
}
