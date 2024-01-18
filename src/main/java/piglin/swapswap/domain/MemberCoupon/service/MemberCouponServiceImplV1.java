package piglin.swapswap.domain.membercoupon.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.coupon.entity.Coupon;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.membercoupon.dto.response.MyCouponGetResponseDto;
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

    @Override
    public List<MyCouponGetResponseDto> getMycouponList(Member member) {

        List<MemberCoupon> memberCouponList = memberCouponRepository.findAllByMemberId(
                member.getId());

        return MemberCouponMapper.memberCouponListToMyCouponResponseDtoList(memberCouponList);
    }
}
