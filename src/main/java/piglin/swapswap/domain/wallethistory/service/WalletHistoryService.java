package piglin.swapswap.domain.wallethistory.service;

import piglin.swapswap.domain.wallet.entity.Wallet;
import piglin.swapswap.domain.wallethistory.entity.WalletHistory;

public interface WalletHistoryService {

    void recordWalletHistory(WalletHistory walletHistory);

    void deleteAllWalletHistoriesByWallet(Wallet wallet);

    void reRegisterWalletHistoryByWallet(Wallet wallet);

}
