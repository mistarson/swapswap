package piglin.swapswap.domain.notification.dto;

import lombok.Builder;
import piglin.swapswap.domain.notification.entity.Notification;

@Builder
public record NotificationResponseDto(

        Long id,
        String url,
        String content,
        boolean status
) {

}
