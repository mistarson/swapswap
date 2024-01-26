package piglin.swapswap.domain.bill.mapper;

import piglin.swapswap.domain.bill.dto.request.BillCreateRequestDto;
import piglin.swapswap.domain.bill.entity.Bill;

public class BillMapper {

    public static Bill createBill(BillCreateRequestDto requestDto) {

        return Bill.builder()
                .extrafee(requestDto.extrafee())
                .isAllowed(false)
                .isTaked(false)
                .isSwapMoneyUsed(false)
                .build();
    }

}
