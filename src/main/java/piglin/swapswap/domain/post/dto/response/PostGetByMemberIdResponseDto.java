package piglin.swapswap.domain.post.dto.response;

import lombok.Builder;
import piglin.swapswap.domain.post.entity.Post;

@Builder
public record PostGetByMemberIdResponseDto(Long postId, String postTitle, String imageUrl) {

    public static PostGetByMemberIdResponseDto toDto(Post post) {
        return PostGetByMemberIdResponseDto.builder()
                .postId(post.getId())
                .postTitle(post.getTitle())
                .imageUrl((String)post.getImageUrl().get(0))
                .build();
    }

}
