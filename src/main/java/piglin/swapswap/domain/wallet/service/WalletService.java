package piglin.swapswap.domain.wallet.service;

import piglin.swapswap.domain.wallet.entity.Wallet;
import piglin.swapswap.domain.wallethistory.constant.HistoryType;

public interface WalletService {

    Wallet createWallet();

    void depositSwapMoney(Long depositSwapMoney, HistoryType historyType, Long memberId);

    void withdrawSwapMoney(Long withdrawSwapMoney, HistoryType historyType, Long memberId);
}
