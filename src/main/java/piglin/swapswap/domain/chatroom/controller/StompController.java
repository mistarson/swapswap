package piglin.swapswap.domain.chatroom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import piglin.swapswap.domain.message.dto.request.MessageRequestDto;
import piglin.swapswap.domain.message.service.MessageServiceImpl;

@Controller
@RequiredArgsConstructor
public class StompController {

    private final MessageServiceImpl messageService;
    private final SimpMessageSendingOperations sendingOperations;

    @MessageMapping("/chat/message")
    public void enter(MessageRequestDto message) {

        messageService.saveMessage(message);

        String destination = "/queue/chat/room" + message.getChatRoomId();
        sendingOperations.convertAndSend(destination, message);
    }
}

