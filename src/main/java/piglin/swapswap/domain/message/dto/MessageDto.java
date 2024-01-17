package piglin.swapswap.domain.message.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import piglin.swapswap.domain.message.constant.MessageType;

@Getter
@Setter
@Builder
public class MessageDto {
    private Long id;
    private MessageType type;
    private String senderNickname;
    private String text;
    private String chatRoomId;
    private LocalDateTime createdTime;
}
