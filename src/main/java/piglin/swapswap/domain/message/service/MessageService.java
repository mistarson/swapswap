package piglin.swapswap.domain.message.service;

import java.util.List;
import piglin.swapswap.domain.message.dto.MessageDto;

public interface MessageService {

    void saveMessage(MessageDto messageDto);

    List<MessageDto> getMessageByChatRoomId(String roomId);
}
