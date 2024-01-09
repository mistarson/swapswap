package piglin.swapswap.domain.post.mapper;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.dto.request.PostCreateRequestDto;
import piglin.swapswap.domain.post.dto.response.PostGetResponseDto;
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

    public static PostGetResponseDto postToGetResponseDto(String author, Post post,
            Long favoriteCnt, boolean favoriteStatus) {

        return new PostGetResponseDto(author, post.getTitle(), post.getContent(),
                post.getCategory().getName(), post.getImageUrl(), post.getViewCnt(),
                post.getUpCnt(), favoriteCnt,
                post.getModifiedTime().format(DateTimeFormatter.ISO_DATE_TIME), favoriteStatus);
    }
}
