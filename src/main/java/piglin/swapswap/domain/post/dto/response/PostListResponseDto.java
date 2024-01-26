package piglin.swapswap.domain.post.dto.response;

import java.util.List;

public record PostListResponseDto (
        List<PostListDetailResponseDto> postList,
        boolean isLast
){

}
