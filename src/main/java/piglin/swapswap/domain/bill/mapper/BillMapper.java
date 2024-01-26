package piglin.swapswap.domain.bill.mapper;

import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.member.entity.Member;

public class BillMapper {

    public static Bill createBill(Long extraFee, Member member) {

        return Bill.builder()
                .extrafee(extraFee)
                .isAllowed(false)
                .isTaked(false)
                .isSwapMoneyUsed(false)
                .member(member)
                .build();
    }

}
