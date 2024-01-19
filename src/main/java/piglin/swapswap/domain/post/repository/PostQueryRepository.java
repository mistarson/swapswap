package piglin.swapswap.domain.post.repository;

import java.time.LocalDateTime;
import java.util.List;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.constant.Category;
import piglin.swapswap.domain.post.constant.City;
import piglin.swapswap.domain.post.dto.response.PostGetListResponseDto;
import piglin.swapswap.domain.post.dto.response.PostGetResponseDto;

public interface PostQueryRepository {

    List<PostGetListResponseDto> findPostListWithFavoriteByCursor(
            Member member, LocalDateTime cursorTime);

    List<PostGetListResponseDto> searchPostListWithFavorite(String title, Category categoryCond,
            City cityCond, Member member, LocalDateTime cursorTime);

    PostGetResponseDto findPostWithFavorite(Long postId, Member member);

    void updatePostViewCnt(Long postId);

    List<PostGetListResponseDto> findAllMyFavoritePost(Member member,
            LocalDateTime cursorTime);
}
