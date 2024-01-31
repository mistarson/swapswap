package piglin.swapswap.domain.deal.repository;

import static piglin.swapswap.domain.deal.entity.QDeal.deal;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import piglin.swapswap.domain.bill.entity.QBill;
import piglin.swapswap.domain.deal.entity.Deal;
import piglin.swapswap.domain.member.entity.QMember;

public class DealQueryRepositoryImpl implements DealQueryRepository {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public DealQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Deal> findAllMyRequestDeal(Long memberId) {

        QBill firstMemberBill = new QBill("firstMemberBill");
        QBill secondMemberBill = new QBill("secondMemberBill");
        QMember firstMember = new QMember("firstMember");
        QMember secondMember = new QMember("secondMember");

        return queryFactory
                .selectFrom(deal)
                .join(deal.firstMemberbill, firstMemberBill).fetchJoin()
                .join(deal.secondMemberbill, secondMemberBill).fetchJoin()
                .join(firstMemberBill.member, firstMember).fetchJoin()
                .join(secondMemberBill.member, secondMember).fetchJoin()
                .where(firstMember.id.eq(memberId))
                .fetch();
    }

    @Override
    public List<Deal> findAllMyReceiveDeal(Long memberId) {

        QBill firstMemberBill = new QBill("firstMemberBill");
        QBill secondMemberBill = new QBill("secondMemberBill");
        QMember firstMember = new QMember("firstMember");
        QMember secondMember = new QMember("secondMember");

        return  queryFactory
                .selectFrom(deal)
                .join(deal.firstMemberbill, firstMemberBill).fetchJoin()
                .join(deal.secondMemberbill, secondMemberBill).fetchJoin()
                .join(firstMemberBill.member, firstMember).fetchJoin()
                .join(secondMemberBill.member, secondMember).fetchJoin()
                .where(secondMember.id.eq(memberId))
                .fetch();
    }

}
