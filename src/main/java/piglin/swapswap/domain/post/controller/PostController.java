package piglin.swapswap.domain.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.dto.request.PostCreateRequestDto;
import piglin.swapswap.domain.post.dto.request.PostUpdateRequestDto;
import piglin.swapswap.domain.post.service.PostService;
import piglin.swapswap.global.annotation.AuthMember;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts/write")
    public String createPost(@Valid @ModelAttribute PostCreateRequestDto requestDto
            , @AuthMember Member member) {

        return "redirect:/posts/" + postService.createPost(member, requestDto);
    }

    @GetMapping("/posts/write")
    public String getPostWriteForm(Model model, @AuthMember Member member) {

        if (member == null) {
            return "redirect:/";
        }

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
    public String getPostList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "modifiedUpTime") String sort,
            Model model, @AuthMember Member member) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Direction.DESC, sort));

        model.addAttribute("PostGetListResponseDtoMap", postService.getPostList(member, pageable));

        return "post/postList";
    }

    @PatchMapping("/posts/{postId}/favorite")
    public ResponseEntity<?> updatePostFavorite(@AuthMember Member member, @PathVariable Long postId) {

        if (member == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        postService.updatePostFavorite(member, postId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/posts/{postId}/write")
    public String updatePost(@Valid @ModelAttribute PostUpdateRequestDto requestDto
            , @AuthMember Member member, @PathVariable Long postId) {

        postService.updatePost(postId, member, requestDto);

        return "redirect:/posts/" + postId;
    }

    @GetMapping("/posts/{postId}/write")
    public String getPostUpdateWriteForm(Model model, @AuthMember Member member,
            @PathVariable Long postId) {

        if (member == null) {
            return "redirect:/";
        }

        model.addAttribute("PostUpdateRequestDto",
                new PostUpdateRequestDto(null, null, null, null));

        model.addAttribute("PostId", postId);

        return "post/postUpdateWrite";
    }
}
