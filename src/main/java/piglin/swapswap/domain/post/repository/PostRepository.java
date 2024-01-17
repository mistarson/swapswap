package piglin.swapswap.domain.post.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostQueryRepository {

    Optional<Post> findByIdAndIsDeletedIsFalse(Long postId);

    List<Post> findByIsDeletedIsTrueAndModifiedTimeBefore(LocalDateTime fourTeenDaysAgo);

    void deleteAllByIsDeletedIsTrueAndModifiedTimeBefore(LocalDateTime fourTeenDaysAgo);
}
