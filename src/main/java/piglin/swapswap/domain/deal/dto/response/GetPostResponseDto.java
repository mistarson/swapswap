package piglin.swapswap.domain.deal.dto.response;

import java.util.Map;
import lombok.Builder;

@Builder
public record GetPostResponseDto(
        Long postId,
        String postTitle,
        Map<Integer, Object> imageUrl
) {


}
