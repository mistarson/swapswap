package piglin.swapswap.domain.chatroom.repository;

import java.util.List;
import java.util.Optional;
import piglin.swapswap.domain.chatroom.dto.ChatRoomResponseDto;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;

public interface ChatRoomQueryRepository {

    List<ChatRoom> findAllByMemberId(Long memberId);

    Optional<ChatRoom> findChatRoomByMemberIds(Long firstMemberId, Long secondMemberId);

}

