package piglin.swapswap.domain.deal.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
public record DealCreateRequestDto (
        @PositiveOrZero
        Long firstExtraFee,
        @PositiveOrZero
        Long secondExtraFee,
        List<Long> firstPostIdList,
        List<Long> secondPostIdList,
        @NotNull
        Long secondMemberId
) {
}
