package piglin.swapswap.domain.membercoupon.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import piglin.swapswap.domain.membercoupon.entity.MemberCoupon;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long>, MemberCouponQueryRepository {

    List<MemberCoupon> findAllByMemberIdAndIsUsedIsFalse(Long memberId);

    Optional<MemberCoupon> findByIdAndIsUsedFalse(Long couponId);
}
