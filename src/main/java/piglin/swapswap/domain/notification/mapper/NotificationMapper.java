package piglin.swapswap.domain.notification.mapper;

import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.notification.constant.NotificationType;
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
}