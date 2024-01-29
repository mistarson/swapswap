package piglin.swapswap.domain.chatroom.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ChatRoomResponseDto(
        Long id,
        String nickname,
        String lastMessage,
        LocalDateTime lastMessageTime
) {

}
