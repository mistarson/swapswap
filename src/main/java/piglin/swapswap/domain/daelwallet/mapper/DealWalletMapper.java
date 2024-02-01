package piglin.swapswap.domain.daelwallet.mapper;

import piglin.swapswap.domain.daelwallet.entity.DealWallet;
import piglin.swapswap.domain.deal.entity.Deal;

public class DealWalletMapper {

    public static DealWallet setDealWallet(Deal deal, Long RequestMemberSwapMoney, Long ReceiveMemberSwapMoney) {

        return DealWallet.builder()
                .deal(deal)
                .RequestMemberSwapMoney(RequestMemberSwapMoney)
                .ReceiveMemberSwapMoney(ReceiveMemberSwapMoney)
                .build();
    }

}
