package piglin.swapswap.domain.bill.service;

import java.util.List;
import piglin.swapswap.domain.bill.dto.request.BillCreateRequestDto;
import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.deal.entity.Deal;
import piglin.swapswap.domain.member.entity.Member;

public interface BillService {

    Bill createBill(Member member, Long extraFee, List<Long> postIdList);
}
