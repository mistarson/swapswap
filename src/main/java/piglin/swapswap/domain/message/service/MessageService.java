package piglin.swapswap.domain.message.service;

import java.util.List;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.message.dto.request.MessageRequestDto;
import piglin.swapswap.domain.message.dto.response.MessageResponseDto;

public interface MessageService {

    void saveMessage(MessageRequestDto messageDto);

    List<MessageResponseDto> getMessageByChatRoomId(String roomId, Member member);
}
