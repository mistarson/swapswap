package piglin.swapswap.domain.bill.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import piglin.swapswap.domain.bill.dto.response.BillSimpleResponseDto;
import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.bill.mapper.BillMapper;
import piglin.swapswap.domain.bill.repository.BillRepository;
import piglin.swapswap.domain.billpost.service.BillPostService;
import piglin.swapswap.domain.deal.entity.Deal;
import piglin.swapswap.domain.deal.repository.DealRepository;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.global.exception.bill.BillNotFoundException;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

@Service
@RequiredArgsConstructor
public class BillServiceImplV1 implements BillService {

    private final BillRepository billRepository;
    private final DealRepository dealRepository;
    private final BillPostService billPostService;

    @Override
    public Bill createBill(Member member, Long extraFee, List<Long> postIdList) {

        Bill bill = BillMapper.createBill(extraFee, member);
        billRepository.save(bill);

        billPostService.createBillPost(bill, postIdList);

        return bill;
    }

    @Override
    public BillSimpleResponseDto getMyBillDto(Long dealId, Member member) {

        Deal deal = dealRepository.findDealByIdWithBillAndMember(dealId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_DEAL_EXCEPTION)
        );

        Bill bill = findBill(deal, member);

        return BillMapper.billToSimpleResponseDto(bill);
    }

    @Override
    public Bill getMyBill(Long billId){

        return billRepository.findById(billId)
                .orElseThrow(() -> new BillNotFoundException(ErrorCode.NOT_FOUND_BILL_EXCEPTION));
    }

    @Override
    @Transactional
    public void updateUsedSwapPay(Long dealId, Member member) {

        Deal deal = dealRepository.findDealByIdWithBillAndMember(dealId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_DEAL_EXCEPTION)
        );

        Bill bill = findBill(deal, member);
        bill.updateUsedSwapMoney();
    }

    @Override
    @Transactional
    public void initialCommission(Long dealId, Member member) {

        Deal deal = dealRepository.findDealByIdWithBillAndMember(dealId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_DEAL_EXCEPTION)
        );

        Bill bill = findBill(deal, member);

        if (bill.getCommission()==null) {
            bill.initialCommission();
        }
    }

    public Bill findBill(Deal deal, Member member){

        if (deal.getFirstMemberbill().getMember().getId().equals(member.getId())) {
            return deal.getFirstMemberbill();
        }

        return deal.getSecondMemberbill();
    }
}
