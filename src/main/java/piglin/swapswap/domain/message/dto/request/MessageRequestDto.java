package piglin.swapswap.domain.message.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import piglin.swapswap.domain.message.constant.MessageType;

@Getter
@Setter
@Builder
public class MessageRequestDto {
    private MessageType type;
    private String senderNickname;
    private String text;
    private Long chatRoomId;
}
