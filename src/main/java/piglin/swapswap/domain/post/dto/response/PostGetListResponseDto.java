package piglin.swapswap.domain.post.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

public record PostGetListResponseDto(
        Long postId,

        Long memberId,

        String title,

        Map<Integer, Object> imageUrl,

        LocalDateTime modifiedUpTime,

        Long viewCnt,

        Long favoriteCnt,

        boolean favoriteStatus
) {

}
