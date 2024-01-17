package piglin.swapswap.domain.post.repository;

import java.time.LocalDateTime;
import java.util.List;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.constant.Category;
import piglin.swapswap.domain.post.dto.response.PostGetListResponseDto;

public interface PostQueryRepository {

    List<PostGetListResponseDto> findPostListWithFavoriteByCursor(
            Member member, LocalDateTime cursorTime);

    List<PostGetListResponseDto> searchPostListWithFavorite(String title, Category categoryCond,
            Member member, LocalDateTime cursorTime);
}