package piglin.swapswap.domain.bill.service;

import java.util.List;
import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.billpost.dto.BillPostResponseDto;
import piglin.swapswap.domain.member.entity.Member;

public interface BillService {

    Bill createBill(Member member, Long extraFee, List<Long> postIdList);
}
