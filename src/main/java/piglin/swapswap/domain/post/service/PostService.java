package piglin.swapswap.domain.post.service;

import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.dto.request.PostCreateRequestDto;

public interface PostService {

    /**
     * 게시글을 생성하는 메소드입니다.
     *
     * @param member   Post 생성 시 연관 관계에 들어갈 member 입니다.
     * @param requestDto Post 를 생성하기 위해서 받는 Request Dto 입니다. Category, Title, Content, ImageUrlList
     *                   로 이루어져 있으며, ImageUrlList는 다중 업로드가 가능합니다.
     */
    Long createPost(Member member, PostCreateRequestDto requestDto);
}
