package piglin.swapswap.domain.deal.repository;

import static piglin.swapswap.domain.deal.entity.QDeal.deal;
import static piglin.swapswap.domain.member.entity.QMember.*;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import piglin.swapswap.domain.deal.dto.response.DealDetailResponseDto;
import piglin.swapswap.domain.deal.dto.response.DealGetResponseDto;
import piglin.swapswap.domain.member.entity.QMember;

public class DealQueryRepositoryImpl implements DealQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;


    public DealQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<DealGetResponseDto> findAllMyDealRequest(Long memberId) {

        return queryFactory.select(
                        Projections.constructor(DealGetResponseDto.class,
                                deal.id,
                                QMember.member.nickname,
                                deal.dealStatus))
                .from(deal)
                .where(deal.firstUserId.eq(memberId))
                .join(QMember.member)
                .on(deal.secondUserId.eq(QMember.member.id))
                .fetch();
    }

    @Override
    public List<DealGetResponseDto> findAllMyDealResponse(Long memberId) {

        return queryFactory.select(
                        Projections.constructor(DealGetResponseDto.class,
                                deal.id,
                                QMember.member.nickname,
                                deal.dealStatus))
                .from(deal)
                .where(deal.secondUserId.eq(memberId))
                .join(QMember.member)
                .on(deal.firstUserId.eq(QMember.member.id))
                .fetch();
    }

    @Override
    public DealDetailResponseDto findDealByIdToDetailResponseDto(Long dealId) {
        return queryFactory
                .select(
                        Projections.constructor(DealDetailResponseDto.class,
                                deal.id,
                                deal.dealStatus,
                                JPAExpressions.select(member.nickname).from(member).where(member.id.eq(deal.firstUserId)),
                                JPAExpressions.select(member.nickname).from(member).where(member.id.eq(deal.secondUserId)),
                                deal.firstPostIdList,
                                deal.secondPostIdList,
                                deal.firstExtraFee,
                                deal.secondExtraFee,
                                deal.firstAllow,
                                deal.secondAllow,
                                deal.firstTake,
                                deal.secondTake
                        ))
                .from(deal)
                .where(deal.id.eq(dealId))
                .fetchOne();
    }
}
