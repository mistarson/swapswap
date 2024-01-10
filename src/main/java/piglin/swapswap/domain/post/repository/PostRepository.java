package piglin.swapswap.domain.post.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import piglin.swapswap.domain.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndIsDeletedIsFalse(Long postId);

    Page<Post> findAllByIsDeletedIsFalse(Pageable pageable);
}
