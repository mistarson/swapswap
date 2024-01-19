package piglin.swapswap.domain.wallet.service;

import piglin.swapswap.domain.wallet.entity.Wallet;

public interface WalletService {

    Wallet createWallet();

    void normalDepositSwapMoney(Long depositSwapMoney, Long memberId);

    void normalWithdrawSwapMoney(Long withdrawSwapMoney, Long memberId);
}
