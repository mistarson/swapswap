package piglin.swapswap.domain.deal.repository;

import java.util.List;
import piglin.swapswap.domain.deal.entity.Deal;

public interface DealQueryRepository {

    List<Deal> findAllMyRequestDeal(Long memberId);

    List<Deal> findAllMyReceiveDeal(Long memberId);
}
