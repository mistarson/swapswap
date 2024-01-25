package piglin.swapswap.domain.wallet.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import piglin.swapswap.domain.wallet.entity.QWallet;
import piglin.swapswap.domain.wallet.entity.Wallet;

public class WalletQueryRepositoryImpl implements WalletQueryRepository {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public WalletQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void reRegister(Wallet wallet) {
        queryFactory
                .update(QWallet.wallet)
                .set(QWallet.wallet.isDeleted, false)
                .where(QWallet.wallet.eq(wallet))
                .execute();
        em.flush();
        em.clear();
    }
}
