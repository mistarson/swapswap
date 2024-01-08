package piglin.swapswap.domain.post.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.repository.MemberRepository;
import piglin.swapswap.domain.post.constant.Category;
import piglin.swapswap.domain.post.constant.PostConstant;
import piglin.swapswap.domain.post.dto.request.PostCreateRequestDto;
import piglin.swapswap.domain.post.entity.Post;
import piglin.swapswap.domain.post.repository.PostRepository;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;
import piglin.swapswap.global.s3.S3ImageServiceImplV1;

@ExtendWith(MockitoExtension.class)
class PostServiceImplV1UnitTest {

    @Mock
    private S3ImageServiceImplV1 s3ImageServiceImplV1;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImplV1 postService;

    private Member member;
    private Long memberId = 1L;

    @BeforeEach
    void setUp() {
        member = Member.builder().id(1L).build();
    }

    @Nested
    @DisplayName("createPost 메소드 테스트 모음")
    class CreatePostTestList {

        @Test
        @DisplayName("게시글 저장 - 성공 / 이미지 1장 업로드")
        void createPost_Success_Image_One() {
            // Given
            List<MultipartFile> imageUrlList = new ArrayList<>();
            imageUrlList.add(Mockito.mock(MultipartFile.class));
            PostCreateRequestDto requestDto = new PostCreateRequestDto(Category.ELECTRONICS, "제목",
                    "내용",
                    imageUrlList);

            when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
            when(s3ImageServiceImplV1.saveImageUrlList(any())).thenReturn(new ArrayList<>());
            // When
            postService.createPost(memberId, requestDto);
            // Then
            verify(postRepository).save(any(Post.class));
        }

        @Test
        @DisplayName("게시글 저장 - 실패 / 이미지를 최소 1장 업로드 해야합니다.")
        void createPost_Fail_Image_None() {
            // Given
            List<MultipartFile> imageUrlList = new ArrayList<>();
            PostCreateRequestDto requestDto = new PostCreateRequestDto(Category.ELECTRONICS, "제목",
                    "내용",
                    imageUrlList);

            when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
            // When - Then
            assertThatThrownBy(() -> postService.createPost(memberId, requestDto))
                    .isInstanceOf(BusinessException.class);
        }

        @Test
        @DisplayName("게시글 저장 - 실패 / 이미지는 최대 n장만 업로드 할 수 있습니다.")
        void createPost_Fail_Image_Over() {
            // Given
            List<MultipartFile> imageUrlList = new ArrayList<>();

            for (int i = 0; i < PostConstant.IMAGE_MAX_SIZE + 1; i++) {
                imageUrlList.add(Mockito.mock(MultipartFile.class));
            }

            PostCreateRequestDto requestDto = new PostCreateRequestDto(Category.ELECTRONICS, "제목",
                    "내용",
                    imageUrlList);

            when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
            // When - Then
            assertThatThrownBy(() -> postService.createPost(memberId, requestDto))
                    .isInstanceOf(BusinessException.class);
        }

        @Test
        @DisplayName("게시글 저장 - 실패 / 유저를 찾을 수 없습니다.")
        void createPost_Fail_User_Not_Found() {
            // Given
            Long invalidMemberId = 999L;
            List<MultipartFile> imageUrlList = new ArrayList<>();
            imageUrlList.add(Mockito.mock(MultipartFile.class));

            PostCreateRequestDto requestDto = new PostCreateRequestDto(Category.ELECTRONICS, "제목",
                    "내용",
                    imageUrlList);

            when(memberRepository.findById(invalidMemberId)).thenThrow(
                    new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
            // When - Then
            assertThatThrownBy(() -> postService.createPost(invalidMemberId, requestDto))
                    .isInstanceOf(BusinessException.class);
        }
    }

}