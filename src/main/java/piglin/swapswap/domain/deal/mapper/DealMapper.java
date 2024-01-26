package piglin.swapswap.domain.deal.mapper;

import java.util.ArrayList;
import java.util.List;
import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.deal.constant.DealStatus;
import piglin.swapswap.domain.deal.dto.response.DealGetReceiveDto;
import piglin.swapswap.domain.deal.dto.response.DealGetRequestDto;
import piglin.swapswap.domain.deal.entity.Deal;

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

}
