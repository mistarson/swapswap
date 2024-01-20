package piglin.swapswap.domain.chatroom.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ChatRoomResponseDto(
        String id,
        String username,
        String lastMessage,
        LocalDateTime lastMessageTime
) {

}
