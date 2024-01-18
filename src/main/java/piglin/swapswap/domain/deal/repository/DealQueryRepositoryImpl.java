package piglin.swapswap.domain.deal.repository;

import static piglin.swapswap.domain.deal.entity.QDeal.deal;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import piglin.swapswap.domain.deal.dto.response.DealGetResponseDto;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.entity.QMember;

public class DealQueryRepositoryImpl implements DealQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;


    public DealQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<DealGetResponseDto> findAllMyDealRequest(Member member) {

        return queryFactory.select(
                Projections.constructor(DealGetResponseDto.class,
                        deal.id,
                        QMember.member.nickname,
                        deal.dealStatus))
                .from(deal)
                .where(deal.firstUserId.eq(member.getId()))
                .join(QMember.member)
                .on(deal.secondUserId.eq(QMember.member.id))
                .fetch();
    }
}
