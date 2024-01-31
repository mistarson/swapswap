package piglin.swapswap.domain.membercoupon.repository;

import piglin.swapswap.domain.member.entity.Member;
import java.util.Optional;
import piglin.swapswap.domain.membercoupon.entity.MemberCoupon;

public interface MemberCouponQueryRepository {

    void deleteAllMemberCouponByMember(Member loginMember);

    void reRegisterCouponByMember(Member loginMember);

    Optional<MemberCoupon> findByIdAndIsUserIsFalseWithMember(Long memberCouponId);
}

