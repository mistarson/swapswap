package piglin.swapswap.domain.deal.mapper;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import piglin.swapswap.domain.deal.constant.DealStatus;
import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.deal.dto.response.DealGetResponseDto;
import piglin.swapswap.domain.deal.entity.Deal;
import piglin.swapswap.domain.post.dto.response.PostGetResponseDto;
import piglin.swapswap.domain.post.entity.Post;

public class DealMapper {

    public static Deal createDeal(DealCreateRequestDto requestDto, Long firstUserId) {

        Map<Integer, Object> firstPostIdListMap = postIdListToMap(requestDto.firstPostIdList());
        Map<Integer, Object> secondPostIdListMap = postIdListToMap(requestDto.secondPostIdList());

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

    private static Map<Integer, Object> postIdListToMap(List<Long> postIdList) {

        Map<Integer, Object> postIdListMap = new HashMap<>();

        for (int i = 0; i < postIdList.size(); i++) {
            postIdListMap.put(i, postIdList.get(i));
        }

        return postIdListMap;
    }
}
