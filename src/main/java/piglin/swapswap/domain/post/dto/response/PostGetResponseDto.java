package piglin.swapswap.domain.post.dto.response;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Builder;
import piglin.swapswap.domain.deal.constant.DealStatus;
import piglin.swapswap.domain.post.constant.Category;
import piglin.swapswap.domain.post.constant.City;

@Builder
public record PostGetResponseDto(
        Long memberId,
        String author,
        String title,
        String content,
        Category category,
        City city,
        DealStatus dealStatus,
        Map<Integer, Object> imageUrl,
        Long viewCnt,
        Long upCnt,
        Long favoriteCnt,
        LocalDateTime modifiedUpTime,
        boolean favoriteStatus
) {

}
