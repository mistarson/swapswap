package piglin.swapswap.domain.post.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.dto.request.PostCreateRequestDto;
import piglin.swapswap.domain.post.dto.request.PostUpdateRequestDto;
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

        return PostGetResponseDto.builder()
                                 .author(post.getMember().getNickname())
                                 .title(post.getTitle())
                                 .content(post.getContent())
                                 .category(post.getCategory().getName())
                                 .imageUrl(post.getImageUrl())
                                 .viewCnt(post.getViewCnt())
                                 .upCnt(post.getUpCnt())
                                 .favoriteCnt(favoriteCnt)
                                 .modifiedUpTime(post.getModifiedUpTime().format(DateTimeFormatter.ISO_DATE_TIME))
                                 .favoriteStatus(favoriteStatus)
                                 .build();
    }

//    public static PostGetListResponseDto postToGetListResponseDto(Post post, Long favoriteCnt,
//            boolean favoriteStatus) {
//
//        return PostGetListResponseDto.builder()
//                                     .postId(post.getId())
//                                     .memberId(post.getMember().getId())
//                                     .title(post.getTitle())
//                                     .imageUrl(post.getImageUrl())
//                                     .modifiedUpTime(post.getModifiedUpTime())
//                                     .viewCnt(post.getViewCnt())
//                                     .favoriteCnt(favoriteCnt)
//                                     .favoriteStatus(favoriteStatus)
//                                     .build();
//    }

    public static void updatePost(Post post, PostUpdateRequestDto requestDto,
            Map<Integer, Object> imageUrlMap) {

        post.updatePost(requestDto.title(), requestDto.content(), imageUrlMap, requestDto.category());
    }

    public static Page<PostGetListResponseDto> toPageDtoList(List<PostGetListResponseDto> responseDtoList, Pageable pageable, Long totalElements) {

        return new PageImpl<>(responseDtoList, pageable, totalElements);
    }
}
