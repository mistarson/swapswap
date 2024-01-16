package piglin.swapswap.domain.post.controller;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.dto.request.PostCreateRequestDto;
import piglin.swapswap.domain.post.dto.request.PostUpdateRequestDto;
import piglin.swapswap.domain.post.dto.response.PostGetListResponseDto;
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

        model.addAttribute("PostId", postId);

        return "post/post";
    }

    @GetMapping("/")
    public String getPostList(
            Model model, @AuthMember Member member,
            @RequestParam(required = false) LocalDateTime cursorTime
    ) {

        model.addAttribute("PostGetListResponseDto", postService.getPostList(member, cursorTime));

        return "post/postList";
    }

    @GetMapping("/posts/more")
    public String getPostListMore(
            Model model, @AuthMember Member member,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime cursorTime
    ) {

        List<PostGetListResponseDto> postList = postService.getPostList(member, cursorTime);

        if (postList.isEmpty()) {
            throw new RuntimeException("더 이상 불러올 게시글이 없습니다");
        }

        model.addAttribute("PostGetListResponseDto", postList);

        return "post/postListFragment";
    }

    @ResponseBody
    @PatchMapping("/posts/{postId}/favorite")
    public ResponseEntity<?> updatePostFavorite(@AuthMember Member member,
            @PathVariable Long postId) {

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

        postService.getPostUpdateWriteForm(member, postId);

        if (member == null) {
            return "redirect:/";
        }

        model.addAttribute("PostUpdateRequestDto",
                new PostUpdateRequestDto(null, null, null, null));

        model.addAttribute("PostId", postId);

        return "post/postUpdateWrite";
    }

    @ResponseBody
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@AuthMember Member member, @PathVariable Long postId) {

        postService.deletePost(member, postId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/search/posts")
    public String searchPost(@RequestParam(required = false) String title,
            @RequestParam(required = false) String category,
            @AuthMember Member member,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model
    ) {

        Pageable pageable = PageRequest.of(page, size);

        model.addAttribute("PostGetListResponseDtoPage", postService.searchPost(title, category, member, pageable));

        return "post/postList";
    }

    @ResponseBody
    @PatchMapping("/posts/{postId}/up")
    public ResponseEntity<?> upPost(@PathVariable Long postId, @AuthMember Member member) {

        postService.upPost(postId, member);

        return ResponseEntity.ok().build();
    }
}
