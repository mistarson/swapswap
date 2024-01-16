package piglin.swapswap.domain.post.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.constant.Category;
import piglin.swapswap.domain.post.dto.response.PostGetListResponseDto;

public interface PostQueryRepository {

    List<PostGetListResponseDto> findAllPostListWithFavoriteAndPaging(
            Member member, LocalDateTime cursorTime);

    List<PostGetListResponseDto> searchPost(String title, Category categoryCond, Member member, LocalDateTime cursorTime);
}
