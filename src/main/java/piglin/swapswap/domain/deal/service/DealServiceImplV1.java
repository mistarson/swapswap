package piglin.swapswap.domain.deal.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import piglin.swapswap.domain.daelwallet.service.DealWalletService;
import piglin.swapswap.domain.deal.constant.DealStatus;
import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.deal.dto.request.DealUpdateRequestDto;
import piglin.swapswap.domain.deal.dto.response.DealDetailResponseDto;
import piglin.swapswap.domain.deal.dto.response.DealGetResponseDto;
import piglin.swapswap.domain.deal.dto.response.DealHistoryResponseDto;
import piglin.swapswap.domain.deal.entity.Deal;
import piglin.swapswap.domain.deal.mapper.DealMapper;
import piglin.swapswap.domain.deal.repository.DealRepository;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.repository.MemberRepository;
import piglin.swapswap.domain.post.service.PostService;
import piglin.swapswap.domain.wallet.service.WalletService;
import piglin.swapswap.domain.wallethistory.constant.HistoryType;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

@Service
@RequiredArgsConstructor
public class DealServiceImplV1 implements DealService {


    private final DealRepository dealRepository;
    private final MemberRepository memberRepository;
    private final DealWalletService dealWalletService;
    private final PostService postService;
    private final WalletService walletService;

    @Override
    public Long createDeal(Member member, DealCreateRequestDto requestDto) {

        existMember(requestDto.secondMemberId());

        Member firstMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
        Deal deal = DealMapper.createDeal(requestDto, firstMember.getId());

        Deal savedDeal = dealRepository.save(deal);

        return savedDeal.getId();
    }

    @Override
    public List<DealGetResponseDto> getMyRequestDealList(Long memberId) {

        return dealRepository.findAllMyDealRequest(memberId);
    }

    @Override
    public List<DealGetResponseDto> getMyResponseDealList(Long memberId) {

        return dealRepository.findAllMyDealResponse(memberId);
    }

    @Override
    public DealDetailResponseDto getDeal(Long dealId, Member member) {

        DealDetailResponseDto responseDto = dealRepository.findDealByIdToDetailResponseDto(dealId);

        isNullDealDetailResponseDto(responseDto);

        return responseDto;
    }

    @Override
    @Transactional
    public void updateDeal(Member member, Long dealId, Long memberId, DealUpdateRequestDto requestDto) {

        Deal deal = findDeal(dealId);

        if (!deal.getFirstUserId().equals(member.getId()) && !deal.getSecondUserId().equals(member.getId())) {
            throw new RuntimeException("딜을 수정할 수 있는 권한이 없어요~");
        }

        if (deal.getDealStatus().equals(DealStatus.DEALING) || deal.getDealStatus().equals(DealStatus.COMPLETED)) {
            throw new RuntimeException("딜을 수정할 수 없는 상태입니다~");
        }

        if (deal.getFirstUserId().equals(memberId)) {
            DealMapper.updateDealFirst(deal, requestDto);
        }

        if (deal.getSecondUserId().equals(memberId)) {
            DealMapper.updateDealSecond(deal, requestDto);
        }
    }

    @Override
    public void checkDeal(Long dealId) {

        dealRepository.findById(dealId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_DEAL_EXCEPTION)
        );
    }

    @Override
    @Transactional
    public void updateDealAllow(Long dealId, Member member) {

        Deal deal = findDeal(dealId);

        if (!deal.getDealStatus().equals(DealStatus.REQUESTED)) {
            throw new BusinessException(ErrorCode.CAN_NOT_UPDATE_ALLOW_STATUS);
        }

        if(deal.getFirstUserId().equals(member.getId())) {

            deal.updateDealFirstMemberAllow();

            Long firstExtraFee = deal.getFirstExtraFee();

            if(deal.getIsFirstSwapMoneyUsed()) {
                if (dealWalletService.existsDealWallet(dealId) ) { // Deal Wallet 이 있다면

                    if (deal.getFirstAllow()) { // 스왑머니를 사용하고, Allow 가 True로 바뀌었으면
                        dealWalletService.updateDealWallet(deal, member, firstExtraFee);
                    }

                    if (!deal.getFirstAllow()) { // 스왑머니를 사용하고, Allow가 False 로 바뀌었으면
                        dealWalletService.withdrawMemberSwapMoneyAtUpdate(deal, member);
                    }
                }

                if (!dealWalletService.existsDealWallet(dealId)) { // Deal Wallet 이 없다면

                    if (deal.getFirstAllow()) {
                        dealWalletService.createDealWallet(deal, member, firstExtraFee);
                    }
                }
            }
        }

        if(deal.getSecondUserId().equals(member.getId())) {

            deal.updateDealSecondMemberAllow();

            Long secondExtraFee = deal.getSecondExtraFee();

            if(deal.getIsSecondSwapMoneyUsed()) {
                if (dealWalletService.existsDealWallet(dealId) ) { // Deal Wallet 이 있다면

                    if (deal.getSecondAllow()) { // 스왑머니를 사용하고, Allow 가 True로 바뀌었으면
                        dealWalletService.updateDealWallet(deal, member, secondExtraFee);
                    }

                    if (!deal.getSecondAllow()) { // 스왑머니를 사용하고, Allow가 False 로 바뀌었으면
                        dealWalletService.withdrawMemberSwapMoneyAtUpdate(deal, member);
                    }
                }

                if (!dealWalletService.existsDealWallet(dealId)) { // Deal Wallet 이 없다면

                    if (deal.getSecondAllow()) {
                        dealWalletService.createDealWallet(deal, member, secondExtraFee);
                    }
                }
            }
        }

        if(deal.getFirstAllow() && deal.getSecondAllow()) {

            deal.updateDealStatus(DealStatus.DEALING);

            List<Long> postIdList = new ArrayList<>();
            for(int i = 0; i<deal.getFirstPostIdList().size(); i++){
                postIdList.add(deal.getFirstPostIdList().get(i));
            }
            for(int i = 0; i<deal.getSecondPostIdList().size(); i++){
                postIdList.add(deal.getSecondPostIdList().get(i));
            }

            postService.updatePostStatusByPostIdList(postIdList, DealStatus.DEALING);
        }
    }

    @Override
    @Transactional
    public void takeDeal(Long dealId, Member member) {

        Deal deal = findDeal(dealId);

        if (!deal.getDealStatus().equals(DealStatus.DEALING)) {
            throw new RuntimeException("인수 할 수 있는 상태가 아닙니다.");
        }

        if(deal.getFirstUserId().equals(member.getId())) {
                deal.updateDealFirstMemberTake();
        }

        if(deal.getSecondUserId().equals(member.getId())) {
                deal.updateDealSecondMemberTake();
        }

        if(deal.getFirstTake() && deal.getSecondTake()) {
            deal.updateDealStatus(DealStatus.COMPLETED);

            List<Long> postIdList = new ArrayList<>();
            for(int i = 0; i<deal.getFirstPostIdList().size(); i++){
                postIdList.add(deal.getFirstPostIdList().get(i));
            }
            for(int i = 0; i<deal.getSecondPostIdList().size(); i++){
                postIdList.add(deal.getSecondPostIdList().get(i));
            }

            postService.updatePostStatusByPostIdList(postIdList, DealStatus.COMPLETED);
        }

        if(!deal.getFirstTake() || !deal.getSecondTake()) {
            deal.updateDealStatus(DealStatus.DEALING);
        }
    }

    @Override
    @Transactional
    public void updateDealSwapMoneyIsUsing(Long dealId, Member member){
        Deal deal = findDeal(dealId);

        if (!deal.getDealStatus().equals(DealStatus.REQUESTED)) {
            throw new BusinessException(ErrorCode.CAN_NOT_UPDATE_ALLOW_STATUS);
        }

        if(deal.getFirstUserId().equals(member.getId())) {
            if(deal.getFirstAllow()) {
                throw new BusinessException(ErrorCode.CAN_NOT_UPDATE_ALLOW_STATUS);
            }
            deal.updateDealFirstMemberSwapMoneyUsing();
        }

        if(deal.getSecondUserId().equals(member.getId())) {
            if(deal.getSecondAllow()) {
                throw new BusinessException(ErrorCode.CAN_NOT_UPDATE_ALLOW_STATUS);
            }
            deal.updateDealSecondMemberSwapMoneyUsing();
        }
    }


    @Override
    public List<DealHistoryResponseDto> getDealHistoryList(Long memberId) {

        List<Deal> dealList = dealRepository.findAllByFirstUserIdOrSecondUserId(
                memberId, memberId);

        return DealMapper.getDealHistory(dealList);
    }

    private Deal findDeal(Long dealId) {

        return dealRepository.findById(dealId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_DEAL_EXCEPTION)
        );
    }

    private void isNullDealDetailResponseDto(DealDetailResponseDto responseDto) {

        if (responseDto.id() == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_DEAL_EXCEPTION);
        }
    }

    private void existMember(Long memberId) {

        if (!memberRepository.existsByIdAndIsDeletedIsFalse(memberId)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_POST_EXCEPTION);
        }
    }

//    private Boolean firstMemberSwapMoneyIsUsing(Deal deal){
//
//        return  deal.getFirstAllow() && deal.getFirstSwapMoneyIsUsing();
//    }
}