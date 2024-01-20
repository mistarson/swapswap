package piglin.swapswap.domain.chatroom.mapper;

import java.util.UUID;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.message.dto.request.MessageRequestDto;

public class ChatRoomMapper {

    public static ChatRoom createChatRoom() {

        return ChatRoom.builder()
                .id(UUID.randomUUID().toString())
                .isDeleted(false)
                .build();
    }

    public static void updateChatRoom(ChatRoom chatRoom, MessageRequestDto requestDto) {

        chatRoom.updateChatRoom(requestDto);
    }

}
