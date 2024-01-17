package piglin.swapswap.global.s3;

import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface S3ImageService {

    List<String> saveImageUrlList(List<MultipartFile> multipartFileList);

    void deleteImageUrlList(Map<Integer, Object> originalImageUrl);
}
