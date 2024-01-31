package piglin.swapswap.domain.bill.repository;

import static piglin.swapswap.domain.bill.entity.QBill.bill;
import static piglin.swapswap.domain.deal.entity.QDeal.deal;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import piglin.swapswap.domain.bill.entity.Bill;


public class BillQueryRepositoryImpl implements  BillQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public BillQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Optional<Bill> findBillByDealIdAndMemberId(Long dealId, Long memberId) {

        BooleanExpression firstMemberCondition = bill.id.eq(
                JPAExpressions
                        .select(deal.firstMemberbill.id)
                        .from(deal)
                        .where(deal.id.eq(dealId).and(deal.firstMemberbill.member.id.eq(memberId)))
        );

        BooleanExpression secondMemberCondition = bill.id.eq(
                JPAExpressions
                        .select(deal.secondMemberbill.id)
                        .from(deal)
                        .where(deal.id.eq(dealId).and(deal.secondMemberbill.member.id.eq(memberId)))
        );

        Bill result = queryFactory
                .selectFrom(bill)
                .where(firstMemberCondition.or(secondMemberCondition))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
