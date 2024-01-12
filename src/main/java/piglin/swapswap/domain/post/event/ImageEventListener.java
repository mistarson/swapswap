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
    public void handleAfterCommitDeleteImage(DeleteImageUrlEvent deleteImageUrlEvent) {
        s3ImageService.deleteImageUrlList(deleteImageUrlEvent.getImageUrl());
    }
}
