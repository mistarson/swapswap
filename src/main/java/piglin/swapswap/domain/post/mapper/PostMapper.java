package piglin.swapswap.domain.post.mapper;

import java.util.Map;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.dto.request.PostCreateRequestDto;
import piglin.swapswap.domain.post.entity.Post;

public class PostMapper {

    public static Post createPost(PostCreateRequestDto requestDto, Map<Integer, Object> imageUrlMap,
            Member member) {
        return Post.builder()
                   .category(requestDto.category())
                   .title(requestDto.title())
                   .content(requestDto.content())
                   .imageUrl(imageUrlMap)
                   .member(member)
                   .upCnt(0L)
                   .viewCnt(0L)
                   .isDeleted(false)
                   .build();
    }
}