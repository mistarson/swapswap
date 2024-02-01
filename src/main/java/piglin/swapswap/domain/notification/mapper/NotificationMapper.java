package piglin.swapswap.domain.notification.mapper;

import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.notification.constant.NotificationType;
import piglin.swapswap.domain.notification.dto.NotificationResponseDto;
import piglin.swapswap.domain.notification.entity.Notification;

public class NotificationMapper {

    public static Notification createNotification(Member receiver, NotificationType notificationType, String content, String url) {

        return Notification.builder()
                .receiver(receiver)
                .notificationType(notificationType)
                .content(content)
                .url(url)
                .isRead(false)
                .build();
    }

    public static NotificationResponseDto createResponseDto(Notification notification) {

        return NotificationResponseDto.builder()
                .id(notification.getId())
                .url(notification.getUrl())
                .content(notification.getContent())
                .status(notification.isRead())
                .build();
    }
}