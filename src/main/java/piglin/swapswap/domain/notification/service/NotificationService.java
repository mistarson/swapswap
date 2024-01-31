package piglin.swapswap.domain.notification.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.notification.constant.NotificationType;
import piglin.swapswap.domain.notification.dto.NotificationCountDto;
import piglin.swapswap.domain.notification.dto.NotificationResponseDto;
import piglin.swapswap.domain.notification.entity.Notification;

public interface NotificationService {

    SseEmitter subscribe(Long id, String lastEventId);

    void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data);

    void sendLostData(String lastEventId, Long memberId, String emitterId, SseEmitter emitter);

    void send(Member receiver, NotificationType notificationType, String content, String url);

    Notification createNotification(Member receiver, NotificationType notificationType, String content, String url);

    List<NotificationResponseDto> findAllNotifications(Long memberId);

    NotificationCountDto countUnReadNotifications(Long memberId);

    void readNotification(Long notificationId);

    void deleteAllByNotifications(Long memberId);

    void deleteByNotifications(Long notificationId);

}
