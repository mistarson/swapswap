package piglin.swapswap.domain.chatroom.mapper;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;
import piglin.swapswap.domain.chatroom.dto.ChatRoomResponseDto;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.chatroom_member.entity.ChatRoomMember;
import piglin.swapswap.domain.message.dto.request.MessageRequestDto;

public class ChatRoomMapper {

    public static ChatRoom createChatRoom() {

        return ChatRoom.builder()
                .id(UUID.randomUUID().toString())
                .build();
    }

    public static void updateChatRoom(ChatRoom chatRoom, MessageRequestDto requestDto) {

        chatRoom.updateChatRoom(requestDto);
    }

    public static ChatRoomResponseDto getChatRoomFromChatRoomMember(ChatRoomMember chatRoomMember) {

        return ChatRoomResponseDto.builder()
                .id(chatRoomMember.getChatRoom().getId())
                .lastMessage(chatRoomMember.getChatRoom().getLastMessage())
                .lastMessageTime(chatRoomMember.getChatRoom().getLastMessageTime())
                .build();
    }

    public static List<ChatRoomResponseDto> getChatRoomFromChatRoomMemberList(List<ChatRoomMember> chatRoomMemberList) {

        return chatRoomMemberList.stream().map(ChatRoomMapper::getChatRoomFromChatRoomMember)
                .sorted(Comparator.comparing(ChatRoomResponseDto::lastMessageTime, Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
    }

}
