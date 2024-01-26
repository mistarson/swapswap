package piglin.swapswap.domain.deal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.bill.service.BillService;
import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.deal.entity.Deal;
import piglin.swapswap.domain.deal.mapper.DealMapper;
import piglin.swapswap.domain.deal.repository.DealRepository;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.service.MemberService;

@Service
@RequiredArgsConstructor
public class DealServiceImplV1 implements DealService {

    private final DealRepository dealRepository;
    private final BillService billService;
    private final MemberService memberService;

    @Override
    @Transactional
    public Long createDeal(Member member, DealCreateRequestDto requestDto) {

        Bill firstMemberBill = billService.createBill(member, requestDto.firstExtraFee(),
                requestDto.firstPostIdList());

        Member secondMember = memberService.getMember(requestDto.secondMemberId());
        Bill secondMemberBill = billService.createBill(secondMember, requestDto.secondExtraFee(),
                requestDto.secondPostIdList());

        Deal deal = dealRepository.save(DealMapper.createDeal(firstMemberBill, secondMemberBill));

        return deal.getId();
    }
}
