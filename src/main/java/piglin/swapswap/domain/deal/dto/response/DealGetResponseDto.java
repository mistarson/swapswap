package piglin.swapswap.domain.deal.dto.response;

import piglin.swapswap.domain.deal.constant.DealStatus;


public record DealGetResponseDto(
        Long dealId,
        String secondUserNickname,
        DealStatus dealStatus

) {

}
