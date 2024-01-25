package piglin.swapswap.domain.message.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import piglin.swapswap.domain.message.constant.MessageType;

@Builder
public record MessageResponseDto (
        Long id,
        MessageType type,
        String text,
        String senderNickname,
        Long chatRoomId,
        LocalDateTime createdTime
) {

}