package piglin.swapswap.domain.post.dto.response;

public record PostGetListResponseDto(
        String title,
        String thumbnailUrl,
        String modifiedTime,
        Long viewCnt,
        Long favoriteCnt,
        boolean favoriteStatus
) {

}
