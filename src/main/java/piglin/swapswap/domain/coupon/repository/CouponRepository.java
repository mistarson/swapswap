package piglin.swapswap.domain.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import piglin.swapswap.domain.coupon.entity.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
