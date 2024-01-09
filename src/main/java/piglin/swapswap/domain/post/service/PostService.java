package piglin.swapswap.domain.post.service;

import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.dto.request.PostCreateRequestDto;
import piglin.swapswap.domain.post.dto.response.PostGetResponseDto;

public interface PostService {

    /**
     * 게시글을 생성하는 메소드입니다.
     *
     * @param memberId   Post 내에 들어갈 memberId 입니다. 이 memberId를 사용하여 예외 처리를 합니다.
     * @param requestDto Post 를 생성하기 위해서 받는 Request Dto 입니다. Category, Title, Content, ImageUrlList
     *                   로 이루어져 있으며, ImageUrlList는 다중 업로드가 가능합니다.
     */
    Long createPost(Long memberId, PostCreateRequestDto requestDto);

    /**
     * 게시글 단 건 조회 메소드입니다.
     * @param postId Post를 검색할 때 쓰이는 PostId 입니다. 이 PostId를 이용하여 예외 처리를 합니다.
     * @param member 익명사용자와 로그인 사용자의 화면을 나누기 위한 member 매개변수 입니다.
     * @return PostGetResponseDto 를 반환하여 Model로 화면에 그려줍니다.
     */
    PostGetResponseDto getPost(Long postId, Member member);
}
