package piglin.swapswap.domain.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.constant.Category;
import piglin.swapswap.domain.post.dto.response.PostGetListResponseDto;
import piglin.swapswap.domain.post.entity.Post;

public interface PostQueryRepository {

    Page<PostGetListResponseDto> searchPost(String title, Category categoryCond, Member member, Pageable pageable);

    Page<PostGetListResponseDto> findAllPostListWithFavoriteAndPaging(Pageable pageable, Member member);
}
