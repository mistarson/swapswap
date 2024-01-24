package piglin.swapswap.domain.membercoupon.repository;

import piglin.swapswap.domain.member.entity.Member;

public interface MemberCouponQueryRepository {

    void deleteAllMemberCouponByMember(Member loginMember);

    void reRegisterCouponByMember(Member loginMember);
}
