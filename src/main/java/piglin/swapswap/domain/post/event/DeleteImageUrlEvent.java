package piglin.swapswap.domain.post.event;

import java.util.Map;
import lombok.Getter;

@Getter
public class DeleteImageUrlEvent {

    private Map<Integer, Object> imageUrl;

    public DeleteImageUrlEvent(Map<Integer, Object> imageUrl) {
        this.imageUrl = imageUrl;
    }
}
