package piglin.swapswap.domain.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import piglin.swapswap.domain.post.constant.Category;
import piglin.swapswap.domain.post.dto.response.PostGetListResponseDto;
import piglin.swapswap.domain.post.entity.Post;

public interface PostQueryRepository {

    Page<Post> searchPost(String title, Category categoryCond, Pageable pageable);
}
