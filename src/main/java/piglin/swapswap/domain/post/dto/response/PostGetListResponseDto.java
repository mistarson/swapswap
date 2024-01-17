package piglin.swapswap.domain.post.dto.response;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
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
