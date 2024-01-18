package piglin.swapswap.domain.post.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.dto.request.PostCreateRequestDto;
import piglin.swapswap.domain.post.dto.request.PostUpdateRequestDto;
import piglin.swapswap.domain.post.dto.response.PostSimpleResponseDto;
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
                   .modifiedUpTime(LocalDateTime.now())
                   .build();
    }

    public static void updatePost(Post post, PostUpdateRequestDto requestDto,
            Map<Integer, Object> imageUrlMap) {

        post.updatePost(requestDto.title(), requestDto.content(), imageUrlMap, requestDto.category());
    }

    public static List<PostSimpleResponseDto> getPostSimpleInfoList(List<Post> postList) {

        return postList.stream().map((post) -> PostSimpleResponseDto.builder()
                        .postId(post.getId())
                        .postTitle(post.getTitle())
                        .imageUrl(post.getImageUrl().get(0).toString())
                        .build())
                .toList();
    }
}
