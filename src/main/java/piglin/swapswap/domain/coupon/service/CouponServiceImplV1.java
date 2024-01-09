package piglin.swapswap.domain.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.coupon.dto.request.CouponCreateRequestDto;
import piglin.swapswap.domain.coupon.entity.Coupon;
import piglin.swapswap.domain.coupon.mapper.CouponMapper;
import piglin.swapswap.domain.coupon.repository.CouponRepository;

@Service
@RequiredArgsConstructor
public class CouponServiceImplV1 implements CouponService {

    private final CouponRepository couponRepository;

    @Override
    public Long createCoupon(CouponCreateRequestDto couponCreateRequestDto) {

        // TODO 추후에 어드민 검증해야함.
        Coupon coupon = CouponMapper.createCoupon(couponCreateRequestDto);

        Coupon savedCoupon = couponRepository.save(coupon);

        return savedCoupon.getId();
    }

}
