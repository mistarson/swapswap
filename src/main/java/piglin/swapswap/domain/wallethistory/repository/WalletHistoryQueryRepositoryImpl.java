package piglin.swapswap.domain.wallethistory.repository;

import static piglin.swapswap.domain.wallethistory.entity.QWalletHistory.walletHistory;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import piglin.swapswap.domain.wallet.entity.Wallet;

public class WalletHistoryQueryRepositoryImpl implements WalletHistoryQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public WalletHistoryQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void deleteAllWalletHistoriesByWallet(Wallet wallet) {
        queryFactory
                .update(walletHistory)
                .set(walletHistory.isDeleted, true)
                .where(walletHistory.wallet.eq(wallet))
                .execute();

        em.flush();
        em.clear();
    }

    @Override
    public void reRegisterWalletHistoryByWallet(Wallet wallet) {
        queryFactory
                .update(walletHistory)
                .set(walletHistory.isDeleted, false)
                .where(walletHistory.wallet.eq(wallet))
                .execute();

        em.flush();
        em.clear();
    }

}
