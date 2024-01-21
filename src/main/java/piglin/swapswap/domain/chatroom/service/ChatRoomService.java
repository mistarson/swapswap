package piglin.swapswap.domain.chatroom.service;

import java.util.List;
import piglin.swapswap.domain.chatroom.dto.ChatRoomResponseDto;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.message.dto.request.MessageRequestDto;

public interface ChatRoomService {

    List<ChatRoomResponseDto> getChatRoomList(Member member);

    Long createChatroom(Member member, Long secondMemberId);

    void leaveChatRoom(Member member, Long roomId);

    ChatRoom findChatRoom(Long id);

    ChatRoom findChatRoomByMessageDto(MessageRequestDto requestDto);
}
