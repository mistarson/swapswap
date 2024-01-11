package piglin.swapswap.global.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

@Service
@RequiredArgsConstructor
public class S3ImageServiceImplV1 implements S3ImageService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public List<String> saveImageUrlList(List<MultipartFile> multipartFileList) {

        List<String> imageUrlList = new ArrayList<>();

        for(MultipartFile multipartFile : multipartFileList) {
            if(!multipartFile.getContentType().startsWith("image")) {
                throw new BusinessException(ErrorCode.IS_NOT_IMAGE);
            }
        }

        for(MultipartFile multipartFile : multipartFileList) {
            String uuidName = UUID.randomUUID().toString();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            metadata.setContentType(multipartFile.getContentType());

            try {
                amazonS3.putObject(bucket, uuidName, multipartFile.getInputStream(), metadata);
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.FAIL_UPLOAD);
            }

            imageUrlList.add(amazonS3.getUrl(bucket, uuidName).toString());
        }

        return imageUrlList;
    }

    @Override
    public void deleteImageUrlList(Map<Integer, Object> originalImageUrl) {

        for(int i = 0; i < originalImageUrl.size(); i++) {

            String[] urlSplit = originalImageUrl.get(i).toString().split("/");
            String objectName = urlSplit[urlSplit.length - 1];

            if(amazonS3.doesObjectExist(bucket, objectName)) {
                amazonS3.deleteObject(bucket, objectName);
            }

        }
    }
}
