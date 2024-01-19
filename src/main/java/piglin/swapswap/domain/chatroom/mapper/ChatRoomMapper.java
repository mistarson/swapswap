package piglin.swapswap.domain.chatroom.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;
import piglin.swapswap.domain.chatroom.dto.ChatRoomResponseDto;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.chatroom_member.entity.ChatRoomMember;

public class ChatRoomMapper {

    public static ChatRoomResponseDto chatRoomToResponseDto(ChatRoom chatRoom) {

        return ChatRoomResponseDto.builder()
                .id(chatRoom.getId())
                .lastMessage(chatRoom.getLastMessage().getText())
                .modifiedTime(chatRoom.getLastMessage().getModifiedTime())
                .build();
    }

    public static ChatRoom createChatRoom() {

        return ChatRoom.builder()
                .id(UUID.randomUUID().toString())
                .build();
    }

    public static List<ChatRoomResponseDto> chatRoomMemberListToChatRoomDtoList(List<ChatRoomMember> chatRoomMemberList, List<ChatRoomResponseDto> chatRoomResponseDtoList) {

        return chatRoomMemberList.stream().map(chatRoomMember -> {

            String lastMessage = "";
            LocalDateTime modifiedTime = null;

            if (chatRoomMember.getChatRoom().getLastMessage() != null) {

                lastMessage = chatRoomMember.getChatRoom().getLastMessage().getText();
                modifiedTime = chatRoomMember.getChatRoom().getCreatedTime();
            }

            return ChatRoomResponseDto.builder()
                    .id(chatRoomMember.getChatRoom().getId())
                    .lastMessage(lastMessage)
                    .modifiedTime(modifiedTime)
                    .build();

        }).toList();
    }
}
