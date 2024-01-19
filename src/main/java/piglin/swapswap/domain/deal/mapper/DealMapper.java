package piglin.swapswap.domain.deal.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import piglin.swapswap.domain.deal.constant.DealStatus;
import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.deal.dto.request.DealUpdateRequestDto;
import piglin.swapswap.domain.deal.entity.Deal;

public class DealMapper {

    public static Deal createDeal(DealCreateRequestDto requestDto, Long firstUserId) {

        Map<Integer, Long> firstPostIdListMap = postIdListToMap(requestDto.firstPostIdList());
        Map<Integer, Long> secondPostIdListMap = postIdListToMap(requestDto.secondPostIdList());

        return Deal.builder()
                .dealStatus(DealStatus.REQUESTED)
                .firstUserId(firstUserId)
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

    public static void updateDealFirst(Deal deal, DealUpdateRequestDto requestDto) {

        Map<Integer, Long> firstPostIdMap = postIdListToMap(requestDto.postIdList());

        deal.updateDealFirst(requestDto.extraFee(), firstPostIdMap);
    }

    public static void updateDealSecond(Deal deal, DealUpdateRequestDto requestDto) {

        Map<Integer, Long> secondPostIdMap = postIdListToMap(requestDto.postIdList());

        deal.updateDealSecond(requestDto.extraFee(), secondPostIdMap);
    }

    private static Map<Integer, Long> postIdListToMap(List<Long> postIdList) {

        Map<Integer, Long> postIdListMap = new HashMap<>();

        for (int i = 0; i < postIdList.size(); i++) {
            postIdListMap.put(i, postIdList.get(i));
        }

        return postIdListMap;
    }
}
