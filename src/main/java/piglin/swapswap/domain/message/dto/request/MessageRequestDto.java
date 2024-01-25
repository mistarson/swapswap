package piglin.swapswap.domain.message.dto.request;

import lombok.Builder;
import piglin.swapswap.domain.message.constant.MessageType;

@Builder
public record MessageRequestDto (
        Long chatRoomId,
        MessageType type,
        String senderNickname,
        String text
) {

}

