package piglin.swapswap.domain.post.dto.request;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import piglin.swapswap.domain.post.constant.Category;

public record PostCreateRequestDto(
        Category category,
        String title,
        String content,
        List<MultipartFile> imageUrlList
) {

}
