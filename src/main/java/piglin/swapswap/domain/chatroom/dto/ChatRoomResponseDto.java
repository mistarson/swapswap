package piglin.swapswap.domain.chatroom.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ChatRoomResponseDto(
        Long id,
        String member1,
        String member2,
        String lastMessage,
        LocalDateTime lastMessageTime
) {

}
