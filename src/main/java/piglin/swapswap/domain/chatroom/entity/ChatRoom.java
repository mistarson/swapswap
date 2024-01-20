package piglin.swapswap.domain.chatroom.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import piglin.swapswap.domain.common.BaseTime;
import piglin.swapswap.domain.message.dto.request.MessageRequestDto;

@Entity
@Getter
@Setter
@Builder
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseTime {

    @Id
    private String id;

    @Column(nullable = true)
    private String lastMessage;

    @Column(nullable = true)
    private LocalDateTime lastMessageTime;

    @Column(nullable = false)
    private Boolean isDeleted;

    public void updateChatRoom(MessageRequestDto requestDto) {
        this.lastMessage = requestDto.getText();
        this.lastMessageTime = LocalDateTime.now();
    }
}
