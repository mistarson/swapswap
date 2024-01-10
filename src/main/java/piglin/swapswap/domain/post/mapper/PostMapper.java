package piglin.swapswap.domain.post.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.dto.request.PostCreateRequestDto;
import piglin.swapswap.domain.post.dto.response.PostGetListResponseDto;
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
                .modifiedUpTime(LocalDateTime.now())
                .build();
    }

    public static PostGetResponseDto postToGetResponseDto(Post post,
            Long favoriteCnt, boolean favoriteStatus) {

        return new PostGetResponseDto(post.getMember().getNickname(), post.getTitle(),
                post.getContent(),
                post.getCategory().getName(), post.getImageUrl(), post.getViewCnt(),
                post.getUpCnt(), favoriteCnt,
                post.getModifiedTime().format(DateTimeFormatter.ISO_DATE_TIME), favoriteStatus);
    }

    public static PostGetListResponseDto postToGetListResponseDto(Post post, Long favoriteCnt,
            boolean favoriteStatus) {

        return new PostGetListResponseDto(post.getTitle(), post.getImageUrl().get(0).toString(),
                post.getModifiedTime().format(DateTimeFormatter.ISO_DATE_TIME), post.getViewCnt(),
                favoriteCnt, favoriteStatus);
    }
}
