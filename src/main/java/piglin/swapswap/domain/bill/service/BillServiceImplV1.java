package piglin.swapswap.domain.bill.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import piglin.swapswap.domain.bill.dto.request.BillUpdateRequestDto;
import piglin.swapswap.domain.bill.dto.response.BillSimpleResponseDto;
import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.bill.mapper.BillMapper;
import piglin.swapswap.domain.bill.repository.BillRepository;
import piglin.swapswap.domain.billpost.dto.BillPostResponseDto;
import piglin.swapswap.domain.billpost.service.BillPostService;
import piglin.swapswap.domain.deal.constant.DealStatus;
import piglin.swapswap.domain.deal.entity.Deal;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.service.PostService;
import piglin.swapswap.global.exception.bill.BillNotFoundException;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;
import piglin.swapswap.global.exception.common.UnauthorizedModifyException;

@Service
@RequiredArgsConstructor
public class BillServiceImplV1 implements BillService {

    private final BillRepository billRepository;
    private final BillPostService billPostService;
    private final PostService postService;

    @Override
    public Bill createBill(Member member, Long extraFee, List<Long> postIdList) {

        Bill bill = BillMapper.createBill(extraFee, member);
        billRepository.save(bill);

        billPostService.createBillPost(bill, postIdList);

        return bill;
    }

    @Override
    public BillSimpleResponseDto getMyBillDto(Long billId) {

        Bill bill = billRepository.findById(billId).orElseThrow(BillNotFoundException::new);

        return BillMapper.billToSimpleResponseDto(bill);
    }

    @Override
    public Bill getMyBill(Long billId){

        return billRepository.findById(billId)
                .orElseThrow(BillNotFoundException::new);
    }

    @Override
    @Transactional
    public void updateUsedSwapPay(Long billId, Member member) {

        Bill bill = billRepository.findByIdWithMember(billId).orElseThrow(
                BillNotFoundException::new);

        validateModifyAuthority(bill.getMember(), member);

        bill.updateUsedSwapMoney();
    }

    @Override
    @Transactional
    public void updateBillAllowWithoutSwapPay(Long billId, Member member) {

        Bill bill = billRepository.findByIdWithMember(billId).orElseThrow(
                BillNotFoundException::new);

        validateModifyAuthority(bill.getMember(), member);

        bill.updateAllow();

        updatePostListDealStatus(bill, getPostIdList(bill));
    }

    @Override
    @Transactional
    public void initialCommission(Long billId, Member member) {

        Bill bill = billRepository.findByIdWithMember(billId).orElseThrow(
                BillNotFoundException::new);

        validateModifyAuthority(bill.getMember(), member);

        if (bill.getCommission()==null) {
            bill.initialCommission();
        }
    }

    @Override
    public void updateBillAllowTrueWithSwapPay(Long billId, Member member) {

        Bill bill = billRepository.findByIdWithMember(billId).orElseThrow(
                BillNotFoundException::new);

        validateModifyAuthority(bill.getMember(), member);

        bill.updateAllow();

        updatePostListDealStatus(bill, getPostIdList(bill));
    }

    @Override
    public void updateBillAllowFalseWithSwapPay(Long billId, Member member) {

        Bill bill = billRepository.findByIdWithMember(billId).orElseThrow(
                BillNotFoundException::new);

        validateModifyAuthority(bill.getMember(), member);

        bill.updateAllow();

        updatePostListDealStatus(bill, getPostIdList(bill));

        bill.initialCommission();
    }

    @Override
    public void validateUpdateBill(Deal deal, Long billId, Member member) {

        Long requestMemberId = deal.getRequestMemberbill().getMember().getId();
        Long receiveMemberId = deal.getReceiveMemberbill().getMember().getId();

        if (!requestMemberId.equals(member.getId()) && !receiveMemberId.equals(member.getId())) {
            throw new UnauthorizedModifyException(ErrorCode.UNAUTHORIZED_MODIFY_DEAL_EXCEPTION);
        }

        if (!deal.getDealStatus().equals(DealStatus.REQUESTED)) {
            throw new BusinessException(ErrorCode.CAN_NOT_UPDATE_CAUSE_DEAL_IS_NOT_REQUESTED);
        }

        Bill bill = null;

        if (deal.getRequestMemberbill().getId().equals(billId)) {
            bill = deal.getRequestMemberbill();
        }
        if (deal.getReceiveMemberbill().getId().equals(billId)) {
            bill = deal.getReceiveMemberbill();
        }

        if (bill.getIsAllowed()) {
            throw new BusinessException(ErrorCode.CAN_NOT_UPDATE_BILL_POST_LIST_CAUSE_IS_NOT_REQUESTED);
        }
    }

    @Override
    public void updateBill(Member member, Long billId, Long memberId, BillUpdateRequestDto requestDto) {

        Bill bill = billRepository.findByIdWithMember(billId).orElseThrow(
                BillNotFoundException::new);

        bill.updateExtraFee(requestDto.extraFee());
    }

    @Override
    public Long getTotalFee(Long billId) {

        Bill bill = billRepository.findById(billId).orElseThrow(
                BillNotFoundException::new);

        return bill.getExtrafee() + bill.getCommission();
    }

    @Override
    @Transactional
    public void updateBillTake(Long billId, Member member) {

        Bill bill = billRepository.findByIdWithMember(billId).orElseThrow(
                BillNotFoundException::new);

        validateModifyAuthority(bill.getMember(), member);

        bill.updateTake();

        updatePostListDealStatus(bill, getPostIdList(bill));
    }

    private void updatePostListDealStatus(Bill bill, List<Long> postIdList) {

        if (bill.getIsAllowed()&&!bill.getIsTaked()) {
            checkPostDealStatus(bill);
            postService.updatePostStatusByPostIdList(postIdList, DealStatus.DEALING);
        }
        if (!bill.getIsAllowed()) {
            postService.updatePostStatusByPostIdList(postIdList, DealStatus.REQUESTED);
        }
        if(bill.getIsTaked()) {
            postService.updatePostStatusByPostIdList(postIdList, DealStatus.COMPLETED);
        }
    }

    private void checkPostDealStatus(Bill bill) {

        List<BillPostResponseDto> billPostDtoList = billPostService.getBillPostDtoList(bill);

        for (BillPostResponseDto billPostResponseDto : billPostDtoList) {
            if (!billPostResponseDto.postStatus().equals(DealStatus.REQUESTED)) {
                throw new BusinessException(ErrorCode.CAN_NOT_UPDATE_POST_STATUS);
            }
        }
    }

    private List<Long> getPostIdList(Bill bill) {

        List<Long> postIdList = new ArrayList<>();
        List<BillPostResponseDto> PostList = billPostService.getBillPostDtoList(bill);

        for (BillPostResponseDto billPostResponseDto : PostList) {
            postIdList.add(billPostResponseDto.postId());
        }

        return postIdList;
    }

    private void validateModifyAuthority(Member billMember, Member loginMember) {

        if (!billMember.getId().equals(loginMember.getId())) {
            throw new UnauthorizedModifyException(ErrorCode.UNAUTHORIZED_MODIFY_DEAL_EXCEPTION);
        }
    }
}
