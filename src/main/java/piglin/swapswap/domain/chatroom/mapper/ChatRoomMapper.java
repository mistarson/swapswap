package piglin.swapswap.domain.chatroom.mapper;

import piglin.swapswap.domain.chatroom.entity.ChatRoom;

public class ChatRoomMapper {

    public static ChatRoom createChatRoom() {

        return ChatRoom.builder()
                .isDeleted(false)
                .build();
    }

}
