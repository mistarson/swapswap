package piglin.swapswap.domain.bill.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import piglin.swapswap.domain.bill.dto.response.BillSimpleResponseDto;
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

    public static BillSimpleResponseDto
    billToSimpleResponseDto(Bill bill) {

        return BillSimpleResponseDto.builder()
                                    .id(bill.getId())
                                    .extraFee(bill.getExtrafee())
                                    .commission(bill.getCommission())
                                    .build();
    }
}
