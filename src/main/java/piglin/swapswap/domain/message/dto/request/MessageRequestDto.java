package piglin.swapswap.domain.message.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import piglin.swapswap.domain.message.constant.MessageType;

@Builder
public record MessageRequestDto (
        Long chatRoomId,
        Long senderId,
        MessageType type,
        String text
) {

}

