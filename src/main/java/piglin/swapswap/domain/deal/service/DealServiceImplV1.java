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
import piglin.swapswap.domain.notification.constant.NotificationType;
import piglin.swapswap.domain.notification.service.NotificationService;
import piglin.swapswap.domain.post.service.PostService;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

@Service
@RequiredArgsConstructor
public class DealServiceImplV1 implements DealService {


    private final DealRepository dealRepository;
    private final MemberRepository memberRepository;
    private final DealWalletService dealWalletService;
    private final PostService postService;
    private final NotificationService notificationService;

    @Override
    public Long createDeal(Member member, DealCreateRequestDto requestDto) {

        existMember(requestDto.secondMemberId());

        Member firstMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));

        Member secondMember = memberRepository.findById(requestDto.secondMemberId())
                .orElseThrow(() ->  new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));

        Deal deal = DealMapper.createDeal(requestDto, firstMember.getId());

        Deal savedDeal = dealRepository.save(deal);

        String Url = "http://localhost:8080/deals/" + deal.getId();
        String content = secondMember.getNickname()+"님! 거래 요청이 왔어요!";
        notificationService.send(secondMember, NotificationType.DEAL,content,Url);

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

        if (dealWalletService.existsDealWallet(dealId)) {

            dealWalletService.withdrawMemberSwapMoneyAtDealUpdate(deal);
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

        if (isNotDealStatusRequested(deal.getDealStatus())) {
            throw new BusinessException(ErrorCode.CAN_NOT_UPDATE_ALLOW_STATUS);
        }

        if (isFirstMember(deal.getFirstUserId(), member.getId())) {
            updateFirstMemberToAllow(deal, member);
        }

        if (isSecondMember(deal.getSecondUserId(), member.getId())) {
            updateSecondMemberToAllow(deal, member);
        }

        if(isBothAllow(deal)) {
            deal.updateDealStatus(DealStatus.DEALING);

            List<Long> postIdList = getDealPostIdList(deal);

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

            if (dealWalletService.existsDealWallet(dealId)) {
                dealWalletService.withdrawMemberSwapMoneyAtComplete(deal);
            }

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

    private boolean isNotDealStatusRequested(DealStatus dealStatus) {

        return !dealStatus.equals(DealStatus.REQUESTED);
    }

    private boolean isFirstMember(Long firstMemberId, Long loginMemberId) {

        return firstMemberId.equals(loginMemberId);
    }

    private boolean isSecondMember(Long secondMemberId, Long loginMemberId) {

        return secondMemberId.equals(loginMemberId);
    }

    private void updateFirstMemberToAllow(Deal deal, Member member) {

        deal.updateDealFirstMemberAllow();

        Long firstExtraFee = null;

        if(deal.getFirstExtraFee() != null) {
            firstExtraFee = deal.getFirstExtraFee();
        }

        if(deal.getIsFirstSwapMoneyUsed()) {
            if (dealWalletService.existsDealWallet(deal.getId()) ) {

                if (deal.getFirstAllow()) {
                    dealWalletService.updateDealWallet(deal, member, firstExtraFee);
                }

                if (!deal.getFirstAllow()) {
                    dealWalletService.withdrawMemberSwapMoneyAtUpdate(deal, member);
                }
            }

            if (!dealWalletService.existsDealWallet(deal.getId())) {

                if (deal.getFirstAllow()) {
                    if (deal.getFirstExtraFee() != null) {
                        dealWalletService.createDealWallet(deal, member, firstExtraFee);
                    }
                }
            }
        }
    }

    private void updateSecondMemberToAllow(Deal deal, Member member) {

        deal.updateDealSecondMemberAllow();

        Long secondExtraFee = null;

        if(deal.getSecondExtraFee() != null) {
            secondExtraFee = deal.getSecondExtraFee();
        }

        if(deal.getIsSecondSwapMoneyUsed()) {
            if (dealWalletService.existsDealWallet(deal.getId()) ) {

                if (deal.getSecondAllow()) {
                    dealWalletService.updateDealWallet(deal, member, secondExtraFee);
                }

                if (!deal.getSecondAllow()) {
                    dealWalletService.withdrawMemberSwapMoneyAtUpdate(deal, member);
                }
            }

            if (!dealWalletService.existsDealWallet(deal.getId())) {

                if (deal.getSecondAllow()) {
                    if (deal.getSecondExtraFee() != null) {
                        dealWalletService.createDealWallet(deal, member, secondExtraFee);
                    }
                }
            }
        }
    }

    private boolean isBothAllow(Deal deal) {

        return deal.getFirstAllow() && deal.getSecondAllow();
    }

    private List<Long> getDealPostIdList(Deal deal) {

        List<Long> postIdList = new ArrayList<>();
        for(int i = 0; i<deal.getFirstPostIdList().size(); i++){
            postIdList.add(deal.getFirstPostIdList().get(i));
        }
        for(int i = 0; i<deal.getSecondPostIdList().size(); i++){
            postIdList.add(deal.getSecondPostIdList().get(i));
        }

        return postIdList;
    }
}