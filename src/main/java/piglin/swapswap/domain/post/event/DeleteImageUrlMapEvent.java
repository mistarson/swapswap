package piglin.swapswap.domain.post.event;

import java.util.Map;
import lombok.Getter;

@Getter
public class DeleteImageUrlMapEvent {

    private Map<Integer, Object> imageUrlMap;

    public DeleteImageUrlMapEvent(Map<Integer, Object> imageUrlMap) {
        this.imageUrlMap = imageUrlMap;
    }
}
