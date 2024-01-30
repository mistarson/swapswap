package piglin.swapswap.domain.message.repository;

import piglin.swapswap.domain.chatroom.entity.ChatRoom;

public interface MessageQueryRepository {

    void deleteAllChatRoomMessage(ChatRoom chatRoom);
}

