package piglin.swapswap.domain.membercoupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.coupon.entity.Coupon;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.membercoupon.entity.MemberCoupon;
import piglin.swapswap.domain.membercoupon.mapper.MemberCouponMapper;
import piglin.swapswap.domain.membercoupon.repository.MemberCouponRepository;

@Service
@RequiredArgsConstructor
public class MemberCouponServiceImplV1 implements MemberCouponService{

    private final MemberCouponRepository memberCouponRepository;


    @Override
    public void saveMemberCoupon(Member member, Coupon coupon) {

        MemberCoupon memberCoupon = MemberCouponMapper.createMemberCoupon(member, coupon);
        memberCouponRepository.save(memberCoupon);
    }
}
