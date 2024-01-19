package piglin.swapswap.domain.deal.repository;

import java.util.List;
import piglin.swapswap.domain.deal.dto.response.DealDetailResponseDto;
import piglin.swapswap.domain.deal.dto.response.DealGetResponseDto;

public interface DealQueryRepository {

    List<DealGetResponseDto> findAllMyDealRequest(Long memberId);

    List<DealGetResponseDto> findAllMyDealResponse(Long memberId);

    DealDetailResponseDto findDealByIdToDetailResponseDto(Long dealId);
}
