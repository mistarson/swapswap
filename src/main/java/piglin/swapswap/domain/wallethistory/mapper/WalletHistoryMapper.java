package piglin.swapswap.domain.wallethistory.mapper;

import piglin.swapswap.domain.wallethistory.constant.HistoryType;
import piglin.swapswap.domain.wallethistory.entity.WalletHistory;

public class WalletHistoryMapper {

    public static WalletHistory createWalletHistory(Long swapMoney, HistoryType historyType) {

        return WalletHistory.builder()
                .historyType(historyType)
                .swapMoney(swapMoney)
                .isDeleted(false)
                .build();
    }

}
