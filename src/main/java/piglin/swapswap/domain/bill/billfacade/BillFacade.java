package piglin.swapswap.domain.bill.billfacade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import piglin.swapswap.domain.bill.dto.request.BillUpdateRequestDto;
import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.bill.service.BillService;
import piglin.swapswap.domain.billcoupon.service.BillCouponService;
import piglin.swapswap.domain.billpost.service.BillPostService;
import piglin.swapswap.domain.daelwallet.service.DealWalletService;
import piglin.swapswap.domain.deal.entity.Deal;
import piglin.swapswap.domain.deal.service.DealService;
import piglin.swapswap.domain.member.entity.Member;

@Component
@RequiredArgsConstructor
public class BillFacade {

    private final DealService dealService;
    private final DealWalletService dealWalletService;
    private final BillService billService;
    private final BillPostService billPostService;
    private final BillCouponService billCouponService;

    @Transactional
    public void updateBillAllowTrueWithSwapPay(Long billId, Member member) {

        billService.updateBillAllowTrueWithSwapPay(billId, member);
        Long totalFee = billService.getTotalFee(billId);
        Deal deal = dealService.getDealByBillIdWithBill(billId);
        if (dealWalletService.existDealWalletByDealId(deal.getId())) {
            dealWalletService.updateDealWallet(deal, member, totalFee);
        } else {
            dealWalletService.createDealWallet(deal, member, totalFee);
        }
    }

    @Transactional
    public void updateBillAllowFalseWithSwapPay(Long billId, Member member) {

        billService.updateBillAllowFalseWithSwapPay(billId, member);
        Deal deal = dealService.getDealByBillIdWithBill(billId);
        dealWalletService.rollbackTemporarySwapMoney(deal);
        billCouponService.initialBillCouponList(billId);
    }

    @Transactional
    public void validateUpdateBill(Long billId, Member member) {

        Deal deal = dealService.getDealByBillIdWithBillAndMember(billId);
        billService.validateUpdateBill(deal, billId, member);
    }

    @Transactional
    public void updateBill(Member member, Long billId, Long memberId,
            BillUpdateRequestDto requestDto) {

        Bill bill = billService.getMyBill(billId);
        Deal deal = dealService.getDealByBillIdWithBillAndMember(billId);
        billService.validateUpdateBill(deal, billId, member);
        billService.updateBill(member, billId, memberId, requestDto);
        billPostService.deleteAllByBill(bill);
        billPostService.createBillPost(bill, requestDto.postIdList());
    }
}