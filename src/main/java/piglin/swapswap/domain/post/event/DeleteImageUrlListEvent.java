package piglin.swapswap.domain.post.event;

import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class DeleteImageUrlListEvent {

    private List<String> imageUrlList;

    public DeleteImageUrlListEvent(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }
}
