package piglin.swapswap.domain.coupon.repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import piglin.swapswap.domain.coupon.entity.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Lock(value = LockModeType.OPTIMISTIC)
    @Query(value = "select c from Coupon c where c.id = :couponId")
    Optional<Coupon> findByIdWithLock(@Param("couponId") Long couponId);

}
