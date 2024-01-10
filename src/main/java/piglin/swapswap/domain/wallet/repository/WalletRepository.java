package piglin.swapswap.domain.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import piglin.swapswap.domain.wallet.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
