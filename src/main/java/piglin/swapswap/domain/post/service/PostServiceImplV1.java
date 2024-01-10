package piglin.swapswap.domain.post.service;

import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.favorite.service.FavoriteService;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.constant.PostConstant;
import piglin.swapswap.domain.post.dto.request.PostCreateRequestDto;
import piglin.swapswap.domain.post.dto.response.PostGetListResponseDto;
import piglin.swapswap.domain.post.dto.response.PostGetResponseDto;
import piglin.swapswap.domain.post.entity.Post;
import piglin.swapswap.domain.post.mapper.PostMapper;
import piglin.swapswap.domain.post.repository.PostRepository;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;
import piglin.swapswap.global.s3.S3ImageServiceImplV1;

@Service
@RequiredArgsConstructor
public class PostServiceImplV1 implements PostService {

    private final FavoriteService favoriteService;
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

    @Override
    @Transactional
    public PostGetResponseDto getPost(Long postId, Member member) {
        Post post = findPost(postId);

        Long favoriteCnt = favoriteService.getPostFavoriteCnt(post);

        boolean favoriteStatus = false;
        if (member != null) {
            favoriteStatus = favoriteService.findFavorite(post, member);
        }

        post.upViewCnt();

        return PostMapper.postToGetResponseDto(post, favoriteCnt, favoriteStatus);
    }

    @Override
    public Map<Long, PostGetListResponseDto> getPostList(Member member) {

        List<Post> postList = postRepository.findAllByIsDeletedIsFalseOrderByModifiedTime();

        Map<Long, PostGetListResponseDto> responseDtoMap = new HashMap<>();

        for(Post post : postList) {
            Long favoriteCnt = favoriteService.getPostFavoriteCnt(post);
            boolean favoriteStatus = false;

            if (member != null) {
                favoriteStatus = favoriteService.findFavorite(post, member);
            }

            PostGetListResponseDto dto = PostMapper.postToGetListResponseDto(post, favoriteCnt, favoriteStatus);

            responseDtoMap.put(post.getId(), dto);
        }

        return responseDtoMap;
    }

    private Post findPost(Long postId) {
        return postRepository.findByIdAndIsDeletedIsFalse(postId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_POST_EXCEPTION)
        );
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
