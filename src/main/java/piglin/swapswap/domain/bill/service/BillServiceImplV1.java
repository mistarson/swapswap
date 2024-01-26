package piglin.swapswap.domain.bill.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.bill.dto.request.BillCreateRequestDto;
import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.bill.mapper.BillMapper;
import piglin.swapswap.domain.bill.repository.BillRepository;
import piglin.swapswap.domain.billpost.service.BillPostService;
import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.member.entity.Member;

@Service
@RequiredArgsConstructor
public class BillServiceImplV1 implements BillService {

    private final BillRepository billRepository;
    private final BillPostService billPostService;

    @Override
    public Bill createBill(Member member, Long extraFee, List<Long> postIdList) {

        Bill bill = BillMapper.createBill(extraFee, member);
        billRepository.save(bill);

        billPostService.createBillPost(bill, postIdList);

        return bill;
    }
}
