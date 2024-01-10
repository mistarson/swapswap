package piglin.swapswap.global.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtCookieManager {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static void addJwtToCookie(String token, HttpServletResponse res) {
        try {
            token = URLEncoder.encode(token, "utf-8")
                    .replaceAll("\\+", "%20");
            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token);
            cookie.setPath("/");

            res.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
    }

    private static Cookie createJwtCookie(String token) {
        Cookie jwtCookie = new Cookie(AUTHORIZATION_HEADER, token);
        jwtCookie.setPath("/");

        return jwtCookie;
    }

    public static void expireTokenCookie(HttpServletResponse response) {

        Cookie jwtCookie = createJwtCookie(null);
        jwtCookie.setMaxAge(0);
        response.addCookie(jwtCookie);
    }

    public static void deleteJwtCookies(HttpServletResponse res) {
        expireTokenCookie(res);
    }
}