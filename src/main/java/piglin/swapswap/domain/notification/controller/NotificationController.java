package piglin.swapswap.domain.notification.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.notification.dto.NotificationCountDto;
import piglin.swapswap.domain.notification.dto.NotificationResponseDto;
import piglin.swapswap.domain.notification.service.NotificationService;
import piglin.swapswap.global.annotation.AuthMember;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@AuthMember Member member,
            @RequestHeader(value = "lastEventID", required = false, defaultValue = "")
            String lastEventId) {

        return notificationService.subscribe(member.getId(), lastEventId);
    }

    @GetMapping("/notifications")
    public List<NotificationResponseDto> findAllNotifications(@AuthMember Member member) {

        return notificationService.findAllNotifications(member.getId());
    }

    @PostMapping("/notification/read/{notificationId}")
    public void readNotification(@PathVariable Long notificationId) {

        notificationService.readNotification(notificationId);
    }

    @GetMapping("/notifications/count")
    public NotificationCountDto countUnReadNotifications(@AuthMember Member member) {

        return notificationService.countUnReadNotifications(member.getId());
    }

    @DeleteMapping("/notifications/delete")
    public ResponseEntity<?> deleteNotifications(@AuthMember Member member) {

        notificationService.deleteAllByNotifications(member);

        return ResponseEntity.ok().body("알림 목록 전체 삭제 성공");
    }

    @DeleteMapping("/notifications/delete/{notificationId}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long notificationId) {

        notificationService.deleteByNotifications(notificationId);

        return ResponseEntity.ok().body("단일 알림 삭제 성공");
    }
}
