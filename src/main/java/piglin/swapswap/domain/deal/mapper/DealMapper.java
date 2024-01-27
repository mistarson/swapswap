package piglin.swapswap.domain.deal.mapper;

import java.util.List;
import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.billcoupon.dto.BillCouponResponseDto;
import piglin.swapswap.domain.billpost.dto.BillPostResponseDto;
import piglin.swapswap.domain.deal.constant.DealStatus;
import piglin.swapswap.domain.deal.dto.response.DealDetailResponseDto;
import piglin.swapswap.domain.deal.dto.response.DealGetReceiveDto;
import piglin.swapswap.domain.deal.dto.response.DealGetRequestDto;
import piglin.swapswap.domain.deal.entity.Deal;
import piglin.swapswap.domain.member.entity.Member;

public class DealMapper {

    public static Deal createDeal(Bill firstMemberbill, Bill secondMemberbill) {

        return Deal.builder()
                .dealStatus(DealStatus.REQUESTED)
                .firstMemberbill(firstMemberbill)
                .secondMemberbill(secondMemberbill)
                .build();
    }

    public static List<DealGetRequestDto> toDealGetRequestDtoList(List<Deal> myRequestDealList) {

        return myRequestDealList.stream().map(deal -> DealGetRequestDto.builder()
                .dealId(deal.getId())
                .secondMemberNickname(deal.getSecondMemberbill().getMember().getNickname())
                .dealStatus(deal.getDealStatus())
                .build()).toList();
    }


    public static List<DealGetReceiveDto> toDealGetReceiveDtoList(List<Deal> myReceiveDealList) {

        return myReceiveDealList.stream().map(deal -> DealGetReceiveDto.builder()
                .dealId(deal.getId())
                .firstMemberNickname(deal.getFirstMemberbill().getMember().getNickname())
                .dealStatus(deal.getDealStatus())
                .build()).toList();
    }

    public static DealDetailResponseDto toDealDetailResponseDto(
            Deal deal,
            List<BillPostResponseDto> requestBillPostListDto,
            List<BillPostResponseDto> receiveBillPostListDto,
            List<BillCouponResponseDto> requestBillCouponDtoList,
            List<BillCouponResponseDto> receiveBillCouponDtoList) {

        Member requestMember = deal.getFirstMemberbill().getMember();
        Member receiveMember = deal.getSecondMemberbill().getMember();

        return DealDetailResponseDto.builder()
                .id(deal.getId())
                .dealStatus(deal.getDealStatus())
                .firstMemberId(requestMember.getId())
                .secondMemberId(receiveMember.getId())
                .firstMemberNickname(requestMember.getNickname())
                .secondMemberNickname(receiveMember.getNickname())
                .firstDealPostList(requestBillPostListDto)
                .secondDealPostList(receiveBillPostListDto)
                .firstExtraFee(deal.getFirstMemberbill().getExtrafee())
                .secondExtraFee(deal.getSecondMemberbill().getExtrafee())
                .firstAllow(deal.getFirstMemberbill().getIsAllowed())
                .secondAllow(deal.getSecondMemberbill().getIsAllowed())
                .firstTake(deal.getFirstMemberbill().getIsTaked())
                .secondTake(deal.getSecondMemberbill().getIsTaked())
                .useSwapMoneyFirstMember(deal.getFirstMemberbill().getIsSwapMoneyUsed())
                .useSwapMoneySecondMember(deal.getSecondMemberbill().getIsSwapMoneyUsed())
                .firstMemberCommission(deal.getFirstMemberbill().getCommission())
                .secondMemberCommission(deal.getSecondMemberbill().getCommission())
                .firstCouponList(requestBillCouponDtoList)
                .secondCouponList(receiveBillCouponDtoList)
                .build();
    }

}
