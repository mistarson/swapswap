package piglin.swapswap.domain.bill.repository;

import java.util.Optional;
import piglin.swapswap.domain.bill.entity.Bill;

public interface BillQueryRepository {

    Optional<Bill> findBillByDealIdAndMemberId(Long billId, Long memberId);
}
