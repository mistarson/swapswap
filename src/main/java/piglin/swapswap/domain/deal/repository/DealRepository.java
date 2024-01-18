package piglin.swapswap.domain.deal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import piglin.swapswap.domain.deal.entity.Deal;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long>, DealQueryRepository {

}
