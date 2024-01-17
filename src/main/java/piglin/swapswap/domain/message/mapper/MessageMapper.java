package piglin.swapswap.domain.message.mapper;

import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.message.dto.MessageDto;
import piglin.swapswap.domain.message.entity.Message;

public class MessageMapper {

    public static Message createMessage(MessageDto messageDto, ChatRoom chatRoom) {

        return Message.builder()
                .type(messageDto.getType())
                .senderNickname(messageDto.getSenderNickname())
                .text(messageDto.getText())
                .chatRoom(chatRoom)
                .build();
    }

}
