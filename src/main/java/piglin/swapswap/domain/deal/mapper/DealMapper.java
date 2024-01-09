package piglin.swapswap.domain.deal.mapper;

import java.util.Map;
import piglin.swapswap.domain.deal.constant.DealStatus;
import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.deal.entity.Deal;

public class DealMapper {

  public static Deal createDeal(DealCreateRequestDto requestDto, Map<Integer, Object> firstPostIdListMap,
      Map<Integer, Object> secondPostIdListMap, DealStatus dealStatus, Boolean firstAllow
      , Boolean secondAllow, Boolean firstTake, Boolean secondTake, Long firstUserId, Long secondUserId ) {
      return Deal.builder()
          .dealStatus(dealStatus)
          .firstUserId(firstUserId)
          .secondUserId(secondUserId)
          .firstAllow(firstAllow)
          .secondAllow(secondAllow)
          .firstTake(firstTake)
          .secondTake(secondTake)
          .firstPostIdList(firstPostIdListMap)
          .secondPostIdList(secondPostIdListMap)
          .firstExtraFee(requestDto.firstExtraFee())
          .secondExtraFee(requestDto.secondExtraFee())
          .build();
  }
}
