package piglin.swapswap.domain.post.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.repository.MemberRepository;
import piglin.swapswap.domain.post.constant.PostConstant;
import piglin.swapswap.domain.post.dto.request.PostCreateRequestDto;
import piglin.swapswap.domain.post.entity.Post;
import piglin.swapswap.domain.post.mapper.PostMapper;
import piglin.swapswap.domain.post.repository.PostRepository;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;
import piglin.swapswap.global.s3.S3ImageServiceImplV1;

@Service
@RequiredArgsConstructor
public class PostServiceImplV1 implements PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final S3ImageServiceImplV1 s3ImageServiceImplV1;

    @Override
    public Long createPost(Member member, PostCreateRequestDto requestDto) {

        imageUrlListSizeCheck(requestDto);

        List<String> imageUrlList = s3ImageServiceImplV1.saveImageUrlList(
                requestDto.imageUrlList());
        Map<Integer, Object> imageUrlMap = new HashMap<>();
        for (int i = 0; i < imageUrlList.size(); i++) {
            imageUrlMap.put(i, imageUrlList.get(i));
        }

        Post post = PostMapper.createPost(requestDto, imageUrlMap, member);

        postRepository.save(post);

        return post.getId();
    }

    private void imageUrlListSizeCheck(PostCreateRequestDto requestDto) {

        if (requestDto.imageUrlList().size() < PostConstant.IMAGE_MIN_SIZE) {
            throw new BusinessException(ErrorCode.POST_IMAGE_MIN_SIZE);
        }
        if (requestDto.imageUrlList().get(0).isEmpty()) {
            throw new BusinessException(ErrorCode.POST_IMAGE_MIN_SIZE);
        }
        if (requestDto.imageUrlList().size() > PostConstant.IMAGE_MAX_SIZE) {
            throw new BusinessException(ErrorCode.POST_IMAGE_MAX_SIZE);
        }
    }
}
