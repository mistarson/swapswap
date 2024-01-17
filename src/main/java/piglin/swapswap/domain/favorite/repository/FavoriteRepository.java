package piglin.swapswap.domain.favorite.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import piglin.swapswap.domain.favorite.entity.Favorite;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.entity.Post;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByMemberAndPost(Member member, Post post);

    Long countByPost(Post post);

    void deleteAllByPostId(Long postId);

    List<Favorite> findAllByPostId(Long postId);
}
