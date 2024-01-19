package piglin.swapswap.domain.deal.dto.response;

import java.util.Map;
import piglin.swapswap.domain.deal.constant.DealStatus;

public record DealDetailResponseDto(
         Long id,

         DealStatus dealStatus,

         Long firstUserId,

         Long secondUserId,

         String firstUserNickname,

         String secondUserNickname,

         Map<Integer, Long> firstPostIdList,

         Map<Integer, Long> secondPostIdList,

         int firstExtraFee,

         int secondExtraFee,

         Boolean firstAllow,

         Boolean secondAllow,

         Boolean firstTake,

         Boolean secondTake
) {

}