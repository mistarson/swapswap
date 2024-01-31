package piglin.swapswap.domain.deal.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.bill.service.BillService;
import piglin.swapswap.domain.billcoupon.dto.BillCouponResponseDto;
import piglin.swapswap.domain.billcoupon.service.BillCouponService;
import piglin.swapswap.domain.billpost.dto.BillPostResponseDto;
import piglin.swapswap.domain.billpost.service.BillPostService;
import piglin.swapswap.domain.daelwallet.service.DealWalletService;
import piglin.swapswap.domain.deal.constant.DealStatus;
import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.deal.dto.response.DealDetailResponseDto;
import piglin.swapswap.domain.deal.dto.response.DealGetReceiveDto;
import piglin.swapswap.domain.deal.dto.response.DealGetRequestDto;
import piglin.swapswap.domain.deal.entity.Deal;
import piglin.swapswap.domain.deal.mapper.DealMapper;
import piglin.swapswap.domain.deal.repository.DealRepository;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.service.MemberService;
import piglin.swapswap.domain.post.service.PostService;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;
import piglin.swapswap.global.exception.deal.DealNotFoundException;

@Service
@RequiredArgsConstructor
public class DealServiceImplV1 implements DealService {

    private final DealRepository dealRepository;
    private final BillService billService;
    private final BillPostService billPostService;
    private final BillCouponService billCouponService;
    private final MemberService memberService;
    private final PostService postService;
    private final DealWalletService dealWalletService;

    @Override
    @Transactional
    public Long createDeal(Member member, DealCreateRequestDto requestDto) {

        Bill firstMemberBill = billService.createBill(member, requestDto.firstExtraFee(),
                requestDto.firstPostIdList());

        Member secondMember = memberService.getMember(requestDto.secondMemberId());
        Bill secondMemberBill = billService.createBill(secondMember, requestDto.secondExtraFee(),
                requestDto.secondPostIdList());

        Deal deal = DealMapper.createDeal(firstMemberBill, secondMemberBill);

        dealRepository.save(deal);

        return deal.getId();
    }

    @Override
    public List<DealGetRequestDto> getMyRequestDealList(Long memberId) {

        List<Deal> myRequestDealList = dealRepository.findAllMyRequestDeal(memberId);

        return DealMapper.toDealGetRequestDtoList(myRequestDealList);
    }

    @Override
    public List<DealGetReceiveDto> getMyReceiveDealList(Long memberId) {

        List<Deal> myReceiveDealList = dealRepository.findAllMyReceiveDeal(memberId);

        return DealMapper.toDealGetReceiveDtoList(myReceiveDealList);
    }

    @Override
    public DealDetailResponseDto getDeal(Long dealId, Member member) {

        Deal deal = dealRepository.findDealByIdWithBillAndMember(dealId).orElseThrow();

        Bill requestMemberBill = deal.getFirstMemberbill();
        Bill receiveMemberBill = deal.getSecondMemberbill();

        List<BillPostResponseDto> requestBillPostDtoList = billPostService.getBillPostDtoList(
                requestMemberBill);
        List<BillPostResponseDto> receiveBillPostDtoList = billPostService.getBillPostDtoList(
                receiveMemberBill);

        List<BillCouponResponseDto> requestBillCouponDtoList = billCouponService.getBillCouponDtoList(
                requestMemberBill);
        List<BillCouponResponseDto> receiveBillCouponDtoList = billCouponService.getBillCouponDtoList(
                receiveMemberBill);

        return DealMapper.toDealDetailResponseDto(deal, requestBillPostDtoList,
                receiveBillPostDtoList, requestBillCouponDtoList, receiveBillCouponDtoList);
    }

    @Override
    @Transactional
    public void updateDealAllowWithoutSwapPay(Long dealId, Member member) {

        Deal deal = dealRepository.findDealByIdWithBill(dealId)
                .orElseThrow(() -> new DealNotFoundException(ErrorCode.NOT_FOUND_DEAL_EXCEPTION));

        Bill myBill = findBill(deal, member);

        myBill.updateAllow();

        updatePostListDealStatus(myBill, getDealPostIdList(myBill));

        bothAllowThenChangeDealing(dealId);
    }

    @Override
    @Transactional
    public void updateDealAllowTrueWithSwapPay(Long dealId, Member member) {

        Deal deal = dealRepository.findDealByIdWithBillAndMember(dealId)
                .orElseThrow(() -> new DealNotFoundException(ErrorCode.NOT_FOUND_DEAL_EXCEPTION));

        Bill myBill = findBill(deal, member);

        checkPostDealStatus(myBill);

        myBill.updateAllow();

        updatePostListDealStatus(myBill, getDealPostIdList(myBill));

        addDealWallet(deal, myBill, member);

        bothAllowThenChangeDealing(dealId);
    }

    @Override
    @Transactional
    public void updateDealAllowFalseWithSwapPay(Long dealId, Member member) {

        Deal deal = dealRepository.findDealByIdWithBillAndMember(dealId)
                .orElseThrow(() -> new DealNotFoundException(ErrorCode.NOT_FOUND_DEAL_EXCEPTION));

        Bill myBill = findBill(deal, member);

        myBill.updateAllow();

        List<Long> postIdList = getDealPostIdList(myBill);

        updatePostListDealStatus(myBill, postIdList);

        removeDealWallet(deal.getId(), deal.getFirstMemberbill().getMember(), deal.getSecondMemberbill()
                .getMember(), member);

        myBill.initialCommission();

        dealWalletService.withdrawMemberSwapMoneyAtDealUpdate(deal);
        billCouponService.initialBillCouponList(myBill.getId());
    }

    private Bill findBill(Deal deal, Member member) {

        if (deal.getFirstMemberbill().getMember().getId().equals(member.getId())) {

            return deal.getFirstMemberbill();
        }

        return deal.getSecondMemberbill();
    }

    private void addDealWallet(Deal deal, Bill bill, Member member) {

        Long totalFee = bill.getExtrafee() + bill.getCommission();

        dealWalletService.createDealWallet(deal, member, totalFee);
    }

    private void removeDealWallet(Long dealId, Member firstMember, Member secondMember, Member loginMember) {

        dealWalletService.removeDealWallet(dealId, firstMember.getId(), secondMember.getId(),
                loginMember.getId());
    }

    private List<Long> getDealPostIdList(Bill bill) {

        List<Long> postIdList = new ArrayList<>();
        List<BillPostResponseDto> PostList = billPostService.getBillPostDtoList(bill);

        for (BillPostResponseDto billPostResponseDto : PostList) {
            postIdList.add(billPostResponseDto.postId());
        }

        return postIdList;
    }

    private void checkPostDealStatus(Bill bill) {

        List<BillPostResponseDto> PostList = billPostService.getBillPostDtoList(bill);

        for (BillPostResponseDto billPostResponseDto : PostList) {
            if (!billPostResponseDto.postStatus().equals(DealStatus.REQUESTED)) {
                throw new BusinessException(ErrorCode.CAN_NOT_UPDATE_POST_STATUS);
            }
        }
    }

    private void updatePostListDealStatus(Bill bill, List<Long> postIdList) {

        if (bill.getIsAllowed()) {
            checkPostDealStatus(bill);
            postService.updatePostStatusByPostIdList(postIdList, DealStatus.DEALING);
        }
        if (!bill.getIsAllowed()) {
            postService.updatePostStatusByPostIdList(postIdList, DealStatus.REQUESTED);
        }
    }

    private void bothAllowThenChangeDealing(Long dealId) {

        Deal deal = dealRepository.findDealByIdWithBillAndMember(dealId)
                .orElseThrow(() -> new DealNotFoundException(ErrorCode.NOT_FOUND_DEAL_EXCEPTION));

        if (deal.getFirstMemberbill().getIsAllowed() && deal.getSecondMemberbill().getIsAllowed()) {
            deal.updateDealStatus(DealStatus.DEALING);
        }
    }
}
