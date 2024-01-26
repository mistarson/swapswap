package piglin.swapswap.domain.message.mapper;

import java.util.List;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.message.constant.MessageType;
import piglin.swapswap.domain.message.dto.request.MessageRequestDto;
import piglin.swapswap.domain.message.dto.response.MessageResponseDto;
import piglin.swapswap.domain.message.entity.Message;

public class MessageMapper {

    public static Message createMessage(Member member, ChatRoom chatRoom, MessageRequestDto messageDto) {

        return Message.builder()
                .memberId(member.getId())
                .type(messageDto.type())
                .text(messageDto.text())
                .isDeleted(false)
                .chatRoom(chatRoom)
                .build();
    }

    public static MessageRequestDto createMessageRequestDto(Long roomId, Long senderId, MessageType type, String text) {

        return MessageRequestDto.builder()
                .chatRoomId(roomId)
                .senderId(senderId)
                .type(type)
                .text(text)
                .build();

    }

    public static MessageResponseDto messageToDto(Message message) {

        return MessageResponseDto.builder()
                .senderId(message.getMemberId())
                .type(message.getType())
                .text(message.getText())
                .createdTime(message.getCreatedTime())
                .build();
    }

    public static List<MessageResponseDto> messageToMessageDto(List<Message> messageList) {

        return messageList.stream().map(MessageMapper::messageToDto).toList();
    }

}

