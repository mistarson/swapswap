package piglin.swapswap.domain.wallethistory.repository;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import piglin.swapswap.domain.wallethistory.entity.WalletHistory;

public interface WalletHistoryRepository extends JpaRepository<WalletHistory, Long>, WalletHistoryQueryRepository{

    void deleteAllByIsDeletedIsTrueAndModifiedTimeBefore(LocalDateTime fourteenDaysAgo);

}
