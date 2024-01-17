package piglin.swapswap.domain.message.service;

import piglin.swapswap.domain.message.dto.MessageDto;

public interface MessageService {

    void saveMessage(MessageDto messageDto);
}
