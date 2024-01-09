package piglin.swapswap.domain.member.contorller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import piglin.swapswap.domain.member.dto.MemberNicknameDto;
import piglin.swapswap.domain.member.service.KakaoServiceImpl;
import piglin.swapswap.domain.member.service.MemberServiceImpl;
import piglin.swapswap.global.jwt.JwtUtil;
import piglin.swapswap.global.security.UserDetailsImpl;
import piglin.swapswap.global.security.UserDetailsServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final UserDetailsServiceImpl userDetailsService;
    private final KakaoServiceImpl kakaoServiceImpl;
    private final MemberServiceImpl userService;

    @GetMapping("/login/kakao/callback")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code, HttpServletResponse response)
            throws JsonProcessingException, UnsupportedEncodingException {
        String token = kakaoServiceImpl.kakaoLogin(code);

        token = URLEncoder.encode(token, "utf-8")
                .replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token);
        cookie.setPath("/");

        response.addCookie(cookie);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, "");
        cookie.setPath("/");

        response.addCookie(cookie);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
