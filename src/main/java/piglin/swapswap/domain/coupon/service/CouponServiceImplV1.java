package piglin.swapswap.domain.coupon.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.coupon.dto.request.CouponCreateRequestDto;
import piglin.swapswap.domain.coupon.dto.response.CouponGetResponseDto;
import piglin.swapswap.domain.coupon.entity.Coupon;
import piglin.swapswap.domain.coupon.mapper.CouponMapper;
import piglin.swapswap.domain.coupon.repository.CouponRepository;
import piglin.swapswap.domain.coupon.validator.CouponValidator;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.membercoupon.service.MemberCouponService;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponServiceImplV1 implements CouponService {

    private final CouponRepository couponRepository;

    private final MemberCouponService memberCouponService;

    @Override
    public Long createCoupon(CouponCreateRequestDto couponCreateRequestDto) {

        CouponValidator.validateExpiredTime(couponCreateRequestDto.expiredTime());

        // TODO 추후에 어드민 검증해야함.
        Coupon coupon = CouponMapper.createCoupon(couponCreateRequestDto);

        Coupon savedCoupon = couponRepository.save(coupon);

        return savedCoupon.getId();
    }

    @Override
    public int getCouponCount(Long couponId) {

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COUPON_EXCEPTION));

        return coupon.getCount();
    }

    @Override
    @Transactional
    public void issueEventCouponByPessimisticLock(Long couponId, Member member) {

        Coupon coupon = couponRepository.findByIdWithPessimisticLock(couponId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COUPON_EXCEPTION));

        if (issueCouponPossible(coupon)) {
            memberCouponService.saveMemberCoupon(member, coupon);
            coupon.issueCoupon();
        }
    }

    @Override
    public void issueEventCouponByOptimisticLock(Long couponId, Member member) {

        Coupon coupon = couponRepository.findByIdWithOptimisticLock(couponId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COUPON_EXCEPTION));

        if (issueCouponPossible(coupon)) {
            memberCouponService.saveMemberCoupon(member, coupon);
            coupon.issueCoupon();
        }
    }

    public boolean issueCouponPossible(Coupon coupon) {

        return coupon.getCount() > 0;
    }

    @Override
    public CouponGetResponseDto getCouponDetail(Long couponId) {

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COUPON_EXCEPTION));

        return CouponMapper.couponToGetResponseDto(coupon);
    }

}