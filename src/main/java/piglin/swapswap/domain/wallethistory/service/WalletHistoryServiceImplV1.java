package piglin.swapswap.domain.wallethistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.wallethistory.entity.WalletHistory;
import piglin.swapswap.domain.wallethistory.repository.WalletHistoryRepository;

@Service
@RequiredArgsConstructor
public class WalletHistoryServiceImplV1 implements WalletHistoryService {

    private final WalletHistoryRepository walletHistoryRepository;

    @Override
    public void recordWalletHistory(WalletHistory walletHistory) {
        walletHistoryRepository.save(walletHistory);
    }
}
