package piglin.swapswap.domain.deal.dto.request;

import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;

public record DealUpdateRequestDto (
        @PositiveOrZero
        int extraFee,
        List<Long> postIdList
) {
}
