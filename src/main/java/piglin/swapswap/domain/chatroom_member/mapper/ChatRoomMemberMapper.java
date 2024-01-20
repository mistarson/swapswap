package piglin.swapswap.domain.chatroom_member.mapper;

import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.chatroom_member.entity.ChatRoomMember;
import piglin.swapswap.domain.member.entity.Member;

public class ChatRoomMemberMapper {

    public static ChatRoomMember createChatRoomMember(ChatRoom chatRoom, Member member) {

        return ChatRoomMember.builder()
                .chatRoom(chatRoom)
                .member(member)
                .build();
    }

}
