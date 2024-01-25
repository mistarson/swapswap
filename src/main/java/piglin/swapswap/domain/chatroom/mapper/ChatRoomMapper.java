package piglin.swapswap.domain.chatroom.mapper;

import java.util.List;
import lombok.RequiredArgsConstructor;
import piglin.swapswap.domain.chatroom.dto.ChatRoomResponseDto;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.member.entity.Member;

@RequiredArgsConstructor
public class ChatRoomMapper {

    public static ChatRoom createChatRoom(Member firstMember, Member secondMember) {

        return ChatRoom.builder()
                .firstMemberId(firstMember.getId())
                .secondMemberId(secondMember.getId())
                .isDeleted(false)
                .build();
    }

}

