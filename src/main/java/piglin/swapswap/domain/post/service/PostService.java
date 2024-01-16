package piglin.swapswap.domain.post.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.dto.request.PostCreateRequestDto;
import piglin.swapswap.domain.post.dto.request.PostUpdateRequestDto;
import piglin.swapswap.domain.post.dto.response.PostGetListResponseDto;
import piglin.swapswap.domain.post.dto.response.PostGetResponseDto;

public interface PostService {

    /**
     * 게시글을 생성하는 메소드입니다.
     *
     * @param member   Post 생성 시 연관 관계에 들어갈 member 입니다.
     * @param requestDto Post 를 생성하기 위해서 받는 Request Dto 입니다. Category, Title, Content, ImageUrlList
     *                   로 이루어져 있으며, ImageUrlList 는 다중 업로드가 가능합니다.
     */
    Long createPost(Member member, PostCreateRequestDto requestDto);

    /**
     * 게시글 단 건 조회 메소드입니다.
     * @param postId Post를 검색할 때 쓰이는 PostId 입니다. 이 PostId를 이용하여 예외 처리를 합니다.
     * @param member 익명사용자와 로그인 사용자의 화면을 나누기 위한 member 매개변수 입니다.
     * @return PostGetResponseDto 를 반환하여 Model 로 화면에 그려줍니다.
     */
    PostGetResponseDto getPost(Long postId, Member member);

    /**
     * 게시글 목록 조회 메소드입니다.
     *
     * @param member favorite 상태를 나타내기 위한 member 매개변수 입니다.
     * @return Paging 처리가 된 PostGetListResponseDto 가 들어갑니다.
     */
    Page<PostGetListResponseDto> getPostList(Member member, Pageable pageable);

    /**
     * 게시글 찜 기능입니다.
     * favorite 은 토글로 작동합니다.
     * @param member Favorite 은 Member 와 Post 다대다 관계로, Favorite 에 member 를 등록하기 위해 사용합니다.
     * @param postId 위와 같은 맥락입니다.
     */
    void updatePostFavorite(Member member, Long postId);

    void updatePost(Long postId, Member member, PostUpdateRequestDto requestDto);

    void getPostUpdateWriteForm(Member member, Long postId);

    void deletePost(Member member, Long postId);

    Page<PostGetListResponseDto> searchPost(String title, String category, Member member, Pageable pageable);

    void upPost(Long postId, Member member);
}
