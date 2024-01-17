package piglin.swapswap.domain.member.contorller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
import piglin.swapswap.domain.member.service.MemberServiceImpl;
import piglin.swapswap.domain.membercoupon.dto.response.MyCouponGetResponseDto;
import piglin.swapswap.domain.membercoupon.service.MemberCouponService;
import piglin.swapswap.global.annotation.AuthMember;
import piglin.swapswap.global.jwt.JwtCookieManager;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final KakaoServiceImpl kakaoServiceImpl;

    private final MemberServiceImpl memberService;

    private final MemberCouponService memberCouponService;

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

    @GetMapping("/members/coupon")
    public String getMyCouponList(@AuthMember Member member, Model model) {

        List<MyCouponGetResponseDto> myCouponGetResponseDtoList = memberCouponService.getMycouponList(member);
        model.addAttribute("myCouponGetResponseDtoList", myCouponGetResponseDtoList);

        return "/member/myCouponList";
    }
}