package piglin.swapswap.domain.member.contorller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import piglin.swapswap.domain.member.dto.MemberNicknameDto;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.service.KakaoServiceImpl;
import piglin.swapswap.domain.member.service.MemberServiceImpl;
import piglin.swapswap.global.annotation.AuthMember;
import piglin.swapswap.global.jwt.JwtCookieManager;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final KakaoServiceImpl kakaoServiceImpl;
    private final MemberServiceImpl memberService;

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

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        JwtCookieManager.expireTokenCookie(response);

        return "redirect:/";
    }


    @PatchMapping("/members/nickname")
    public String  updateNickname(@AuthMember Member member,
            @Valid @RequestBody MemberNicknameDto requestDto) {

        memberService.updateNickname(member, requestDto);
        return "redirect:/userinfo";
    }

    @DeleteMapping("/members")
    public String unregister(@AuthMember Member member, HttpServletResponse response) {

        memberService.deleteMember(member);
        JwtCookieManager.expireTokenCookie(response);

        return "redirect:/";
    }
}