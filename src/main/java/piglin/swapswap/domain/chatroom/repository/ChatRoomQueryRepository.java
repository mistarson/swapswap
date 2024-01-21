package piglin.swapswap.domain.chatroom.repository;

import java.util.List;
import java.util.Optional;
import piglin.swapswap.domain.chatroom.dto.ChatRoomResponseDto;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;

public interface ChatRoomQueryRepository {

    List<ChatRoomResponseDto> findAllByMemberIdWithMember(Long memberId);

    Optional<ChatRoom> findByMyMemberIdAndOpponentMemberId(Long myMemberId, Long OpponentMemberId);

}
