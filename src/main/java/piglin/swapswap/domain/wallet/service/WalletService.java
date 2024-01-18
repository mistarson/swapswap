package piglin.swapswap.domain.wallet.service;

import piglin.swapswap.domain.wallet.entity.Wallet;

public interface WalletService {

    Wallet createWallet();
    void depositSwapMoney(Long swapMoney, Long member);
}
