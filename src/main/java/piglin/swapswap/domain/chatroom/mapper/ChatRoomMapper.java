package piglin.swapswap.domain.chatroom.mapper;

import java.util.List;
import piglin.swapswap.domain.chatroom.dto.ChatRoomResponseDto;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.member.entity.Member;

public class ChatRoomMapper {

    public static ChatRoom createChatRoom(Member member1, Member member2) {

        return ChatRoom.builder()
                .member1(member1)
                .member2(member2)
                .isDeleted(false)
                .build();
    }

    public static ChatRoomResponseDto getChatRoomResponseDto(ChatRoom chatRoom) {

        return ChatRoomResponseDto.builder()
                .id(chatRoom.getId())
                .lastMessage(chatRoom.getLastMessage())
                .lastMessageTime(chatRoom.getLastMessageTime())
                .member1(getMemberNickname(chatRoom.getMember1()))
                .member2(getMemberNickname(chatRoom.getMember2()))
                .build();
    }

    public static List<ChatRoomResponseDto> getChatRoomResponseDtoList(List<ChatRoom> chatRoomList) {

        return chatRoomList.stream().map(ChatRoomMapper::getChatRoomResponseDto).toList();
    }

    private static String getMemberNickname(Member member) {
        return (member != null) ? member.getNickname() : null;
    }

}
