package piglin.swapswap.domain.chatroom.service;

import java.util.List;
import piglin.swapswap.domain.chatroom.dto.ChatRoomResponseDto;
import piglin.swapswap.domain.member.entity.Member;

public interface ChatRoomService {

    List<ChatRoomResponseDto> getChatRoomList(Member member);

    ChatRoomResponseDto findById(String roomId, Member member);

    String createChatroom(Member member, Long secondMemberId);

    void leaveChatRoom(Member member, String roomId);
}
