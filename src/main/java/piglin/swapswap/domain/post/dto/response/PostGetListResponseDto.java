package piglin.swapswap.domain.post.dto.response;

import java.time.LocalDateTime;
import java.util.Map;
import piglin.swapswap.domain.deal.constant.DealStatus;
import piglin.swapswap.domain.post.constant.City;

public record PostGetListResponseDto(
        Long postId,

        Long memberId,

        City city,

        DealStatus dealStatus,

        String title,

        Map<Integer, Object> imageUrl,

        LocalDateTime modifiedUpTime,

        Long viewCnt,

        Long favoriteCnt,

        boolean favoriteStatus
) {

}
