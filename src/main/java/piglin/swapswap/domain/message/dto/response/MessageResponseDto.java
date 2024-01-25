package piglin.swapswap.domain.message.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import piglin.swapswap.domain.message.constant.MessageType;

@Builder
public record MessageResponseDto (
        Long senderId,
        MessageType type,
        String text,
        LocalDateTime createdTime
) {

}