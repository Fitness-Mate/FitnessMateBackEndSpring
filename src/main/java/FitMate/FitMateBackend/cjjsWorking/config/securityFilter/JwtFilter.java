package FitMate.FitMateBackend.cjjsWorking.config.securityFilter;

import FitMate.FitMateBackend.cjjsWorking.exception.CustomErrorCode;
import FitMate.FitMateBackend.cjjsWorking.exception.JwtFilterException;
import FitMate.FitMateBackend.cjjsWorking.service.authService.JwtService;
import FitMate.FitMateBackend.cjjsWorking.service.storageService.RedisCacheService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String accessToken;
        final String loginId;
        final String uri = request.getRequestURI();;

        if(uri.equals("/auth/refresh")) { //토큰 재발급을 위한 요청인 경우
            filterChain.doFilter(request, response);
            return;
        }
        if(authHeader == null) {
            if(uri.equals("/auth/login") || uri.equals("user/auth") || uri.equals("user/auth/jwt/admin/register")) {
                filterChain.doFilter(request, response);
                return;
            }
            //register, login 요청 이외의 요청에 대해 토큰이 없을 경우
            throw new JwtFilterException(CustomErrorCode.JWT_NOT_FOUND_EXCEPTION);
        }
        if(!authHeader.startsWith("Bearer ")) {
            //Bearer로 시작하지 않을 경우
            throw new JwtFilterException(CustomErrorCode.NON_START_BEARER_EXCEPTION);
        }

        accessToken = authHeader.substring("Bearer ".length());
        loginId = JwtService.getLoginEmail(accessToken); //JwtFilterException 발생 부분

        log.info("loginId: [{}]", loginId);
        if(loginId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginId);

            if(jwtService.validateToken(accessToken, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
