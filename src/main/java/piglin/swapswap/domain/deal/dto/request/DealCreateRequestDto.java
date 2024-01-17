package piglin.swapswap.domain.deal.dto.request;

import java.util.List;
public record DealCreateRequestDto (
     int firstExtraFee,
     int secondExtraFee,
     List<Long> firstPostIdList,
     List<Long> secondPostIdList,
     Long secondMemberId
) {
}
