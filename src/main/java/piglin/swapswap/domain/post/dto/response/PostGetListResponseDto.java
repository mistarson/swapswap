package piglin.swapswap.domain.post.dto.response;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class PostGetListResponseDto {
    Long postId;
    Long memberId;
    String title;
    Map<Integer, Object> imageUrl;
    LocalDateTime modifiedUpTime;
    Long viewCnt;
    Long favoriteCnt;
    boolean favoriteStatus;
}
