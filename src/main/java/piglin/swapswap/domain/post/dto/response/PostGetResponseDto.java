package piglin.swapswap.domain.post.dto.response;

import java.util.Map;

public record PostGetResponseDto(
        String author,
        String title,
        String content,
        String category,
        Map<Integer, Object> imageUrl,
        Long viewCnt,
        Long upCnt,
        Long favoriteCnt,
        String modifiedTime,
        boolean favoriteStatus
) {

}
