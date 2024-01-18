package piglin.swapswap.domain.member.repository;

import static piglin.swapswap.domain.member.entity.QMember.member;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.wallet.entity.QWallet;

public class MemberQueryRepositoryImpl implements MemberQueryRepository {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public MemberQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Member> findByIdWithWallet(Long memberId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(member)
                        .join(member.wallet, QWallet.wallet)
                        .fetchJoin()
                        .where(memberIdEq(memberId), isNotDeleted())
                        .fetchOne());
    }

    private BooleanExpression memberIdEq(Long memberId) {

        return member.id.eq(memberId);
    }

    private BooleanExpression isNotDeleted() {

        return member.isDeleted.isFalse();
    }
}
