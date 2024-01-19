package piglin.swapswap.domain.deal.repository;

import java.util.List;
import piglin.swapswap.domain.deal.dto.response.DealGetResponseDto;
import piglin.swapswap.domain.member.entity.Member;

public interface DealQueryRepository {

    List<DealGetResponseDto> findAllMyDealRequest(Long memberId);

    List<DealGetResponseDto> findAllMyDealResponse(Long memberId);
}
