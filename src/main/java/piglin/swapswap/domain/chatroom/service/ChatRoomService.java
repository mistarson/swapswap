package piglin.swapswap.domain.chatroom.service;

import java.util.List;
import piglin.swapswap.domain.chatroom.dto.ChatRoomResponseDto;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.message.dto.MessageDto;

public interface ChatRoomService {

    List<ChatRoomResponseDto> getChatRoomList(Member member);

    List<MessageDto> getMessageByChatRoomId(String roomId);

    ChatRoomResponseDto findById(String roomId);
}
