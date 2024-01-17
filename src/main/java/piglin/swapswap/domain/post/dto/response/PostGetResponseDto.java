package piglin.swapswap.domain.post.dto.response;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Builder;
import piglin.swapswap.domain.post.constant.Category;

@Builder
public record PostGetResponseDto(
        String author,
        String title,
        String content,
        Category categoryEnum,
        Map<Integer, Object> imageUrl,
        Long viewCnt,
        Long upCnt,
        Long favoriteCnt,
        LocalDateTime modifiedUpTime,
        boolean favoriteStatus
) {

}
