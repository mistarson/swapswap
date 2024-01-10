package piglin.swapswap.global.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtCookieManager {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtUtil jwtUtil;

    public JwtCookieManager(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public void addJwtToCookie(String token, HttpServletResponse res) {
        try {
            token = URLEncoder.encode(token, "utf-8")
                    .replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행
            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token); // Name-Value
            cookie.setPath("/");

            // Response 객체에 Cookie 추가
            res.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
    }

    public void deleteJwtCookies(HttpServletResponse res) {
        Cookie cookie = new Cookie(AUTHORIZATION_HEADER, ""); // Name-Value
        cookie.setPath("/");

        // Response 객체에 Cookie 추가
        res.addCookie(cookie);
        jwtUtil.expireTokenCookie(res);
    }
}