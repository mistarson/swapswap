package piglin.swapswap.domain.post.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import piglin.swapswap.global.s3.S3ImageService;

@Component
@RequiredArgsConstructor
public class ImageEventListener {

    private final S3ImageService s3ImageService;

    @TransactionalEventListener
    public void handleAfterCommitDeleteImageUrlMap(DeleteImageUrlMapEvent deleteImageUrlMapEvent) {
        s3ImageService.deleteImageUrlMap(deleteImageUrlMapEvent.getImageUrlMap());
    }

    @TransactionalEventListener
    public void handleAfterCommitDeleteImageUrlList(DeleteImageUrlListEvent deleteImageUrlListEvent) {
        s3ImageService.deleteImageUrlList(deleteImageUrlListEvent.getImageUrlList());
    }
}
