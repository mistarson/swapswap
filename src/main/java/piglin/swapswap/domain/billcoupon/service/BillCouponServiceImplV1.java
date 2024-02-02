package piglin.swapswap.domain.billcoupon.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.bill.service.BillService;
import piglin.swapswap.domain.billcoupon.dto.BillCouponResponseDto;
import piglin.swapswap.domain.billcoupon.entity.BillCoupon;
import piglin.swapswap.domain.billcoupon.mapper.BillCouponMapper;
import piglin.swapswap.domain.billcoupon.repository.BillCouponRepository;
import piglin.swapswap.domain.coupon.constant.CouponType;
import piglin.swapswap.domain.billcoupon.dto.RedeemCouponRequestDto;
import piglin.swapswap.domain.coupon.entity.Coupon;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.membercoupon.entity.MemberCoupon;
import piglin.swapswap.domain.membercoupon.service.MemberCouponService;
import piglin.swapswap.global.exception.common.ErrorCode;
import piglin.swapswap.global.exception.coupon.DuplicateCouponTypeException;
import piglin.swapswap.global.exception.deal.InvalidDealRequestException;

@Service
@RequiredArgsConstructor
public class BillCouponServiceImplV1 implements BillCouponService {

    private final BillCouponRepository billCouponRepository;

    private final MemberCouponService memberCouponService;

    private final BillService billService;

    @Override
    @Transactional
    public void redeemCoupons(Long billId, Member member, RedeemCouponRequestDto dealRedeemCouponRequestDto) {

        validateSelectedCouponIdList(dealRedeemCouponRequestDto.selectedCouponIdList(),
                member.getId());

        Bill myBill = billService.getMyBill(billId);

        applyCouponsToBill(dealRedeemCouponRequestDto.selectedCouponIdList(), myBill);
    }

    @Override
    public List<BillCouponResponseDto> getBillCouponDtoList(Bill bill) {

        return billCouponRepository.findMemberCouponFromBillCouponByBill(bill);
    }

    @Override
    public Long createBillCoupon(Bill bill, MemberCoupon memberCoupon) {

        return billCouponRepository.save(BillCouponMapper.createBillCoupon(bill, memberCoupon)).getId();
    }

    @Override
    public boolean existsBillCoupon(Bill bill) {

        return billCouponRepository.existsByBillId(bill.getId());
    }

    @Override
    public void deleteBillCouponByBillId(Long billId) {

        billCouponRepository.deleteAllByBillId(billId);
    }

    @Override
    @Transactional
    public void initialBillCouponList(Long billId) {

        Bill bill = billService.getMyBill(billId);
        bill.initialCommission();

        List<BillCoupon> billCouponList = billCouponRepository.findByBillWithMemberCoupon(bill);
        for (BillCoupon billCoupon : billCouponList) {
            MemberCoupon memberCoupon = billCoupon.getMemberCoupon();
            memberCoupon.rollbackCoupon();
        }

        billCouponRepository.deleteAllByBill(billCouponList);
    }

    public void validateSelectedCouponIdList(List<Long> selectedCouponIdList, Long memberId) {

        Set<CouponType> duplicateCouponTypeSet = new HashSet<>();

        for (Long memberCouponId : selectedCouponIdList) {
            MemberCoupon memberCoupon = memberCouponService.getMemberCouponWithCouponByMemberCouponId(memberCouponId);

            if (!memberCoupon.getMember().getId().equals(memberId)) {
                throw new InvalidDealRequestException(ErrorCode.IS_NOT_MY_COUPON);
            }

            CouponType couponType = memberCoupon.getCoupon().getCouponType();
            if (duplicateCouponTypeSet.contains(couponType)) {
                throw new DuplicateCouponTypeException();
            }
            duplicateCouponTypeSet.add(couponType);
        }
    }

    public void applyCouponsToBill(List<Long> selectedCouponIdList, Bill bill) {

        int commissionDiscountPercent = 0;
        for (Long memberCouponId : selectedCouponIdList) {
            MemberCoupon memberCoupon = memberCouponService.getMemberCouponWithCouponById(memberCouponId);
            Coupon coupon = memberCoupon.getCoupon();

            switch (coupon.getCouponType()) {
                case FEE -> commissionDiscountPercent += getCommissionDiscountPercent(coupon);
            }
            memberCoupon.useCoupon();
            billCouponRepository.save(BillCouponMapper.createBillCoupon(bill, memberCoupon));
        }

        bill.discountCommission(commissionDiscountPercent);
    }

    public int getCommissionDiscountPercent(Coupon coupon) {

        return coupon.getDiscountPercentage();
    }
}
