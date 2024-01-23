package piglin.swapswap.domain.message.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import piglin.swapswap.domain.message.constant.MessageType;

@Builder
public record MessageResponseDto (
        Long id,
        MessageType type,
        String senderNickname,
        String text,
        Long chatRoomId,
        LocalDateTime createdTime
) {

}