package piglin.swapswap.domain.deal.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import piglin.swapswap.domain.deal.constant.DealStatus;
import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.deal.entity.Deal;
import piglin.swapswap.domain.member.entity.Member;

public class DealMapper {

  public static Deal createDeal(DealCreateRequestDto requestDto, Member member) {

    Map<Integer, Object> firstPostIdListMap = new HashMap<>();
    Map<Integer, Object> secondPostIdListMap = new HashMap<>();
    List<Long> firstPostIdList = requestDto.firstPostIdList();
    List<Long> secondPostIdList = requestDto.secondPostIdList();

    for(int i=0; i< firstPostIdList.size(); i++) {
      firstPostIdListMap.put(i, firstPostIdList.get(i));
    }

    for (int i=0; i< secondPostIdList.size(); i++) {
      secondPostIdListMap.put(i, secondPostIdList.get(i));
    }

      return Deal.builder()
          .dealStatus(DealStatus.REQUESTED)
          .firstUserId(member.getId())
          .secondUserId(requestDto.secondMemberId())
          .firstAllow(true)
          .secondAllow(false)
          .firstTake(false)
          .secondTake(false)
          .firstPostIdList(firstPostIdListMap)
          .secondPostIdList(secondPostIdListMap)
          .firstExtraFee(requestDto.firstExtraFee())
          .secondExtraFee(requestDto.secondExtraFee())
          .build();
  }
}
