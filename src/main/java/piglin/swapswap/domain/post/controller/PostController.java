package piglin.swapswap.domain.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import piglin.swapswap.domain.post.dto.request.PostCreateRequestDto;
import piglin.swapswap.domain.post.service.PostService;
import piglin.swapswap.global.security.UserDetailsImpl;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/write")
    public String createPost(@Valid @ModelAttribute PostCreateRequestDto requestDto
            , @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return "redirect:/posts/" + postService.createPost(userDetails.getUserId(), requestDto)
                .toString();
    }

    @GetMapping("/write")
    public String getPostWriteForm(Model model) {
        model.addAttribute("PostCreateRequestDto",
                new PostCreateRequestDto(null, null, null, null));
        return "post/postWrite";
    }
}
