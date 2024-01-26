package piglin.swapswap.domain.chatroom.service;

import java.util.List;
import piglin.swapswap.domain.chatroom.dto.ChatRoomResponseDto;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.message.dto.request.MessageRequestDto;
import piglin.swapswap.domain.message.dto.response.MessageResponseDto;

public interface ChatRoomService {

    ChatRoomResponseDto getChatRoomResponseDto(Long roomId, Long memberId);
    List<ChatRoomResponseDto> getChatRoomList(Member member);

    List<MessageResponseDto> getMessageByChatRoomId(Long roomId, Member member);

    Long createChatroom(Member member, Long secondMemberId);

    void saveMessage(MessageRequestDto requestDto);

    void leaveChatRoom(Member member, Long roomId);
}