package piglin.swapswap.domain.message.mapper;

import java.util.List;
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

    public static List<MessageDto> messageToMessageDto(List<Message> messageList) {

        return messageList.stream().map(message -> MessageDto.builder()
                .id(message.getId())
                .type(message.getType())
                .senderNickname(message.getSenderNickname())
                .text(message.getText())
                .chatRoomId(message.getChatRoom().getId())
                .createdTime(message.getCreatedTime())
                .build()).toList();
    }

}
