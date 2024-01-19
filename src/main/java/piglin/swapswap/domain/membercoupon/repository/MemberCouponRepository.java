package piglin.swapswap.domain.membercoupon.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import piglin.swapswap.domain.membercoupon.entity.MemberCoupon;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    List<MemberCoupon> findAllByMemberId(Long memberId);

}
