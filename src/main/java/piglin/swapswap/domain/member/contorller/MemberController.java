package piglin.swapswap.domain.member.contorller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import piglin.swapswap.domain.member.dto.MemberNicknameDto;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.service.KakaoServiceImpl;
import piglin.swapswap.domain.member.service.MemberService;
import piglin.swapswap.domain.post.dto.response.PostGetListResponseDto;
import piglin.swapswap.domain.post.service.PostService;
import piglin.swapswap.global.annotation.AuthMember;
import piglin.swapswap.global.jwt.JwtCookieManager;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final KakaoServiceImpl kakaoServiceImpl;
    private final MemberService memberService;
    private final PostService postService;

    @GetMapping("/login/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response)
            throws Exception {

        try {
            String accessToken = kakaoServiceImpl.kakaoLogin(code);
            JwtCookieManager.addJwtToCookie(accessToken, response);

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return "redirect:/";
    }

    @GetMapping("/members/logout")
    public String logout(HttpServletResponse response) {
        JwtCookieManager.expireTokenCookie(response);

        return "redirect:/";
    }


    @ResponseBody
    @PatchMapping("/members/nickname")
    public ResponseEntity<?> updateNickname(@AuthMember Member member,
            @Valid @RequestBody MemberNicknameDto requestDto) {

        memberService.updateNickname(member, requestDto);
        return ResponseEntity.ok().build();
    }
    @ResponseBody
    @DeleteMapping("/members")
    public ResponseEntity<?> unregister(@AuthMember Member member, HttpServletResponse response) {

        memberService.deleteMember(member);
        JwtCookieManager.expireTokenCookie(response);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/members/swap-money")
    public String getMySwapMoney(@AuthMember Member member, Model model) {

        Long mySwapMoney = memberService.getMySwapMoney(member.getId());
        model.addAttribute("mySwapMoney", mySwapMoney);

        return "member/mySwapMoney";
    }

    @GetMapping("/members/favorites")
    public String getMyFavoriteList(@AuthMember Member member,@RequestParam(required = false) LocalDateTime cursorTime, Model model) {

        List<PostGetListResponseDto> responseDtoList = postService.getMyFavoritePostList(
                member, cursorTime);
        model.addAttribute("postGetListResponseDto", responseDtoList);

        return "post/postFavoriteList";
    }

    @GetMapping("/members/favorites/more")
    public String getMyFavoriteListMore(@AuthMember Member member, LocalDateTime cursorTime, Model model) {

        List<PostGetListResponseDto> responseDtoList = postService.getMyFavoritePostList(
                member, cursorTime);
        model.addAttribute("postGetListResponseDto", responseDtoList);

        return "post/postListFragment";
    }
}