package piglin.swapswap.domain.post.dto.response;

import java.util.Map;
import lombok.Builder;

@Builder
public record PostGetResponseDto(
        Long userId,
        String author,
        String title,
        String content,
        String category,
        Map<Integer, Object> imageUrl,
        Long viewCnt,
        Long upCnt,
        Long favoriteCnt,
        String modifiedUpTime,
        boolean favoriteStatus
) {

}
