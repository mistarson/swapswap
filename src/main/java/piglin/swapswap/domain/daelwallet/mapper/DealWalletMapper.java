package piglin.swapswap.domain.daelwallet.mapper;

import piglin.swapswap.domain.daelwallet.entity.DealWallet;
import piglin.swapswap.domain.deal.entity.Deal;

public class DealWalletMapper {

    public static DealWallet setDealWallet(Deal deal, Long firstSwapMoney, Long secondSwapMoney) {

        return DealWallet.builder()
                .deal(deal)
                .firstSwapMoney(firstSwapMoney)
                .secondSwapMoney(secondSwapMoney)
                .build();
    }

}
