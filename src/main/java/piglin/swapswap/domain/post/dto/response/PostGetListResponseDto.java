package piglin.swapswap.domain.post.dto.response;

import lombok.Builder;

@Builder
public record PostGetListResponseDto(
        String title,
        String thumbnailUrl,
        String modifiedUpTime,
        Long viewCnt,
        Long favoriteCnt,
        boolean favoriteStatus
) {

}
