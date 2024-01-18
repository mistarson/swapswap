package piglin.swapswap.domain.wallethistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import piglin.swapswap.domain.wallethistory.entity.WalletHistory;

public interface WalletHistoryRepository extends JpaRepository<WalletHistory, Long> {

}
