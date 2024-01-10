package piglin.swapswap.domain.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.dto.request.PostCreateRequestDto;
import piglin.swapswap.domain.post.service.PostService;
import piglin.swapswap.global.annotation.AuthMember;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts/write")
    public String createPost(@Valid @ModelAttribute PostCreateRequestDto requestDto
            , @AuthMember Member member) {

        return "redirect:/posts/" + postService.createPost(member, requestDto)
                .toString();
    }

    @GetMapping("/posts/write")
    public String getPostWriteForm(Model model) {

        model.addAttribute("PostCreateRequestDto",
                new PostCreateRequestDto(null, null, null, null));

        return "post/postWrite";
    }

    @GetMapping("/posts/{postId}")
    public String getPost(@PathVariable Long postId, Model model, @AuthMember Member member) {

        model.addAttribute("PostGetResponseDto", postService.getPost(postId, member));

        return "post/post";
    }

    @GetMapping("/")
    public String getPostList(Model model, @AuthMember Member member) {

        model.addAttribute("PostGetListResponseDtoMap", postService.getPostList(member));

        return "post/postList";
    }
}
