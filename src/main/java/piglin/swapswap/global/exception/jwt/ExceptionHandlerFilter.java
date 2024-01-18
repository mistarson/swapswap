package piglin.swapswap.global.exception.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.filter.OncePerRequestFilter;
import piglin.swapswap.global.exception.user.UserNotFoundException;
import piglin.swapswap.global.jwt.JwtCookieManager;

@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
            handleTokenError(response);
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다");
            handleTokenError(response);
        } catch (JwtInvalidException e) {
            log.error("유효하지 않은 JWT 토큰입니다");
            handleTokenError(response);
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다.");
            handleTokenError(response);
        } catch (NoSuchElementException e) {
            log.error("회원 정보를 찾을 수 없습니다.");
            handleTokenError(response);
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error("토큰을 추출 할 수 없습니다.");
            handleTokenError(response);
        } catch (NoJwtException e) {
            log.error("이 요청은 JWT가 필요합니다.");
            handleTokenError(response);
        } catch (UnsupportedGrantTypeException e) {
            log.error("지원하지 않는 권한 부여 유형입니다.");
            handleTokenError(response);
        } catch (UserNotFoundException e) {
            log.error("데이터베이스에 존재하지 않는 유저입니다.");
            handleTokenError(response);
        } catch (NullPointerException e) {
            handleTokenError(response);
        }
    }

    private void handleTokenError(HttpServletResponse response) throws IOException {
        JwtCookieManager.expireTokenCookie(response);
        response.sendRedirect("/login");
    }
}
