package piglin.swapswap.global.jwt;


import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import piglin.swapswap.global.exception.jwt.JwtInvalidException;

@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
            FilterChain filterChain) throws ServletException, IOException {

        String tokenValue = jwtUtil.getTokenFromRequest(req);

        if (StringUtils.hasText(tokenValue)) {
            tokenValue = jwtUtil.substringToken(tokenValue);

            if (!jwtUtil.validateToken(tokenValue)) {
                throw new JwtInvalidException();
            }

            Claims claims = jwtUtil.getUserInfoFromToken(tokenValue);
            String email = String.valueOf(claims.get(JwtUtil.CLAIM_USER_EMAIL));

            try {
                setAuthentication(email);
            } catch (Exception e) {
                throw e;
            }
        }

        filterChain.doFilter(req, res);
    }

    public void setAuthentication(String email) {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(email);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String email) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
    }
}
