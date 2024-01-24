package piglin.swapswap.domain.wallet.repository;

import piglin.swapswap.domain.wallet.entity.Wallet;

public interface WalletQueryRepository {

    void reRegister(Wallet wallet);
}
