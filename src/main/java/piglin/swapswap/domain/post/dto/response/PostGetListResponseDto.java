package piglin.swapswap.domain.post.dto.response;

import lombok.Builder;

@Builder
public record PostGetListResponseDto(
        Long postId,
        Long memberId,
        String title,
        String thumbnailUrl,
        String modifiedUpTime,
        Long viewCnt,
        Long favoriteCnt,
        boolean favoriteStatus
) {

}
