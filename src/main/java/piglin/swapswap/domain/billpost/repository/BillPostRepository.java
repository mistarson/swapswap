package piglin.swapswap.domain.billpost.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.billpost.entity.BillPost;
import piglin.swapswap.domain.post.entity.Post;

public interface BillPostRepository extends JpaRepository<BillPost, Long> {

    Optional<BillPost> findByBillAndPost(Bill bill, Post post);
}
