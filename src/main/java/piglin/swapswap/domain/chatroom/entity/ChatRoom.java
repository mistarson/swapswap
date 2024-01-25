package piglin.swapswap.domain.chatroom.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import piglin.swapswap.domain.common.BaseTime;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

@Entity
@Getter
@Builder
@DynamicUpdate
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private Long firstMemberId;

    @Column(nullable = true)
    private Long secondMemberId;

    @Column(nullable = true)
    private String lastMessage;

    @Column(nullable = true)
    private LocalDateTime lastMessageTime;

    @Column(nullable = false)
    private Boolean isDeleted;

    public void deleteChatRoom() {
        isDeleted = true;
    }

    public void updateChatRoom(String lastMessage) {
        this.lastMessage = lastMessage;
        this.lastMessageTime = LocalDateTime.now();
    }

    public void leaveChatRoom(Member member) {

        if (member.getId().equals(firstMemberId)) {

            firstMemberId = null;
        } else if (member.getId().equals(secondMemberId)) {

            secondMemberId = null;
        } else {

            throw new BusinessException(ErrorCode.NOT_CHAT_ROOM_MEMBER_EXCEPTION);
        }
    }
}
