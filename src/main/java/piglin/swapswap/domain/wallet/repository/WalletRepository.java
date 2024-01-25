package piglin.swapswap.domain.wallet.repository;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import piglin.swapswap.domain.wallet.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long>, WalletQueryRepository {

    void deleteAllByIsDeletedIsTrueAndModifiedTimeBefore(LocalDateTime fourteenDaysAgo);
}
