package piglin.swapswap.domain.membercoupon.repository;

import java.util.Optional;
import piglin.swapswap.domain.membercoupon.entity.MemberCoupon;

public interface MemberCouponQueryRepository {

    Optional<MemberCoupon> findByIdAndIsUserIsFalseWithMember(Long memberCouponId);

}
