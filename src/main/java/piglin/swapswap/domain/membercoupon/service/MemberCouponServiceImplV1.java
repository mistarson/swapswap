package piglin.swapswap.domain.membercoupon.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.coupon.entity.Coupon;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.membercoupon.dto.response.MyCouponGetResponseDto;
import piglin.swapswap.domain.membercoupon.entity.MemberCoupon;
import piglin.swapswap.domain.membercoupon.mapper.MemberCouponMapper;
import piglin.swapswap.domain.membercoupon.repository.MemberCouponRepository;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

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

        List<MemberCoupon> memberCouponList = memberCouponRepository.findAllByMemberIdAndIsUsedIsFalse(
                member.getId());

        return MemberCouponMapper.memberCouponListToMyCouponResponseDtoList(memberCouponList);
    }

    @Override
    public MemberCoupon getMemberCouponById(Long memberCouponId) {

        return  memberCouponRepository.findByIdAndIsUsedFalse(memberCouponId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_COUPON_EXCEPTION)
        );
    }

    @Override
    public void deleteMemberCoupon(MemberCoupon memberCoupon) {

        memberCouponRepository.delete(memberCoupon);
    }

    @Override
    public MemberCoupon getMemberCouponByIdWithMember(Long memberCouponId) {

        return memberCouponRepository.findByIdAndIsUserIsFalseWithMember(memberCouponId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COUPON_EXCEPTION));
    }
}
