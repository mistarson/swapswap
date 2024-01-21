package piglin.swapswap.domain.message.mapper;

import java.util.List;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.message.dto.request.MessageRequestDto;
import piglin.swapswap.domain.message.dto.response.MessageResponseDto;
import piglin.swapswap.domain.message.entity.Message;

public class MessageMapper {

    public static Message createMessage(MessageRequestDto messageDto, ChatRoom chatRoom) {

        return Message.builder()
                .type(messageDto.getType())
                .senderNickname(messageDto.getSenderNickname())
                .text(messageDto.getText())
                .isDeleted(false)
                .chatRoom(chatRoom)
                .build();
    }

    public static MessageResponseDto messageToDto(Message message) {

        return MessageResponseDto.builder()
                .id(message.getId())
                .type(message.getType())
                .senderNickname(message.getSenderNickname())
                .text(message.getText())
                .chatRoomId(message.getChatRoom().getId())
                .createdTime(message.getCreatedTime())
                .build();
    }

    public static List<MessageResponseDto> messageToMessageDto(List<Message> messageList) {

        return messageList.stream().map(MessageMapper::messageToDto).toList();
    }

}
