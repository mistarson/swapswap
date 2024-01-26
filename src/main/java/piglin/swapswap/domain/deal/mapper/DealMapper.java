package piglin.swapswap.domain.deal.mapper;

import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.deal.constant.DealStatus;
import piglin.swapswap.domain.deal.entity.Deal;

public class DealMapper {

    public static Deal createDeal(Bill firstMemberbill, Bill secondMemberbill) {

        return Deal.builder()
                .dealStatus(DealStatus.REQUESTED)
                .firstMemberbill(firstMemberbill)
                .secondMemberbill(secondMemberbill)
                .build();
    }

}
