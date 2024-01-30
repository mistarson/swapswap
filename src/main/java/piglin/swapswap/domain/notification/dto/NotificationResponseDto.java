package piglin.swapswap.domain.notification.dto;

import lombok.Builder;

@Builder
public record NotificationResponseDto(

        Long id,
        String url,
        String content,
        boolean status
) {

}
