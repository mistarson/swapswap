package piglin.swapswap.domain.deal.dto.response;

import java.util.Map;
import piglin.swapswap.domain.deal.constant.DealStatus;

public record DealDetailResponseDto(
         Long id,

         DealStatus dealStatus,

         String firstUserNickname,

         String secondUserNickname,

         Map<Integer, Object> firstPostIdList,

         Map<Integer, Object> secondPostIdList,

         int firstExtraFee,

         int secondExtraFee,

         Boolean firstAllow,

         Boolean secondAllow,

         Boolean firstTake,

         Boolean secondTake
) {

}
