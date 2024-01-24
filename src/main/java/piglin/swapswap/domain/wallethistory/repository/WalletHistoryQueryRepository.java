package piglin.swapswap.domain.wallethistory.repository;

import piglin.swapswap.domain.wallet.entity.Wallet;

public interface WalletHistoryQueryRepository {

    void deleteAllWalletHistoriesByWallet(Wallet wallet);

    void reRegisterWalletHistoryByWallet(Wallet wallet);
}
