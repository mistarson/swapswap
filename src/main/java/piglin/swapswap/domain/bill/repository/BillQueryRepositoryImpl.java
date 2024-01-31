package piglin.swapswap.domain.bill.repository;

import static piglin.swapswap.domain.bill.entity.QBill.bill;
import static piglin.swapswap.domain.deal.entity.QDeal.deal;
import static piglin.swapswap.domain.member.entity.QMember.*;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import javax.swing.text.html.Option;
import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.bill.entity.QBill;
import piglin.swapswap.domain.member.entity.QMember;


public class BillQueryRepositoryImpl implements  BillQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public BillQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Bill> findByIdWithMember(Long billId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(bill)
                .join(bill.member, member)
                .where(billIdEq(billId))
                .fetchOne());

    }

    private BooleanExpression billIdEq(Long billId) {

        return bill.id.eq(billId);
    }
}
