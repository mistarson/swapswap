package piglin.swapswap.domain.deal.dto.response;

import java.util.Map;
import piglin.swapswap.domain.deal.constant.DealStatus;

public record DealDetailResponseDto(
    Long id,

    DealStatus dealStatus,

    Long firstMemberId,

    Long secondMemberId,

    String firstMemberNickname,

    String secondMemberNickname,

    Map<Integer, Long> firstPostIdList,

    Map<Integer, Long> secondPostIdList,

    Long firstExtraFee,

    Long secondExtraFee,

    Boolean firstAllow,

    Boolean secondAllow,

    Boolean firstTake,

    Boolean secondTake,

    Boolean isFirstSwapMoneyUsed,

    Boolean isSecondSwapMoneyUsed
    ) {
}
