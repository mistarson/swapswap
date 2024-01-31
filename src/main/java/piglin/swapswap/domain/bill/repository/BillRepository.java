package piglin.swapswap.domain.bill.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.deal.entity.Deal;
import piglin.swapswap.domain.member.entity.Member;

public interface BillRepository extends JpaRepository<Bill, Long>, BillQueryRepository {

    boolean existsByIdAndMemberId(Long billId, Long MemberId);

    Optional<Bill> findByIdAndMemberId(Long billId, Long MemberId);
}
