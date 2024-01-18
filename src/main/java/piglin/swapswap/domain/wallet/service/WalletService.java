package piglin.swapswap.domain.wallet.service;

import piglin.swapswap.domain.wallet.entity.Wallet;

public interface WalletService {

    Wallet createWallet();
    void noramlDepositSwapMoney(Long swapMoney, Long member);
}
