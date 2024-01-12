package piglin.swapswap.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import piglin.swapswap.domain.post.constant.Category;

public record PostUpdateRequestDto(
        Category category,
        @Size(max = 50, message = "제목은 최소 1자 이상, 최대 50자 까지 입력 할 수 있습니다.")
        @NotBlank(message = "제목은 필수 입력란입니다.")
        String title,
        @Size(max = 1000, message = "글 내용은 최소 1자 이상, 최대 1000자 까지 입력 할 수 있습니다.")
        @NotBlank(message = "글 내용은 필수 입력란입니다.")
        String content,
        List<MultipartFile> imageUrlList
) {

}
