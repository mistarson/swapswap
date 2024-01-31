package piglin.swapswap.domain.bill.service;

import java.util.List;
import piglin.swapswap.domain.bill.dto.response.BillSimpleResponseDto;
import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.member.entity.Member;

public interface BillService {

    Bill createBill(Member member, Long extraFee, List<Long> postIdList);

    BillSimpleResponseDto getMyBillDto(Long dealId, Member member);

    Bill getMyBill(Long billId);

//    void updateAllowWithoutSwapPay(Long dealId, Member member);

//    void updateAllowWithSwapPay(Long dealId, Member member);

    void updateUsedSwapPay(Long dealId, Member member);

     void initialCommission(Long dealId, Member member);
}
