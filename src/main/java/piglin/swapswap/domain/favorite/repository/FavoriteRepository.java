package piglin.swapswap.domain.favorite.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import piglin.swapswap.domain.favorite.entity.Favorite;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.entity.Post;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> , FavoriteQueryRepository{

    Optional<Favorite> findByMemberAndPost(Member member, Post post);

    Long countByPost(Post post);

    void deleteAllByIsDeletedIsTrueAndModifiedTimeBefore(LocalDateTime modifiedTime);

    List<Favorite> findAllByPostId(Long postId);
}
