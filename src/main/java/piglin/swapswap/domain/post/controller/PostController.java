package piglin.swapswap.domain.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import piglin.swapswap.domain.post.dto.request.PostCreateRequestDto;
import piglin.swapswap.domain.post.service.PostService;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public String createPost(@Valid @ModelAttribute PostCreateRequestDto requestDto,
            BindingResult bindingResult
            , @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        if (bindingResult.hasErrors()) {
            return "redirect:/posts/write";
        }

        return "redirect:/posts/" + postService.createPost(userDetails.getUser().getId(), requestDto).toString();
    }
}
