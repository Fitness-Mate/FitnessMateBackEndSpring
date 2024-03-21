package FitMate.FitMateBackend.common.jwt;

import FitMate.FitMateBackend.common.exception.CustomErrorCode;
import FitMate.FitMateBackend.common.exception.CustomException;
import FitMate.FitMateBackend.user.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j(topic = "JwtUtil")
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "authorization";

    private final String BEARER_PREFIX = "Bearer ";
    private final String AUTHORIZATION_KEY = "auth";

    private final Long ACCESS_TIME = 60 * 60 * 1000L;
    private final Long REFRESH_TIME = 7 * 24 * 60 * 60 * 1000L;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private Key key;

    @Value("${jwt.secret_key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String generateAccessToken(String email, UserRole role) {
        Date date = new Date();

        return BEARER_PREFIX +
            Jwts.builder()
                .setSubject(email)
                .claim(AUTHORIZATION_KEY, role)
                .setExpiration(new Date(date.getTime() + ACCESS_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public String generateRefreshToken() {
        Date date = new Date();

        //refresh token 내부에는 사용자의 정보를 담지 않는다.
        return BEARER_PREFIX +
            Jwts.builder()
                .setExpiration(new Date(date.getTime() + REFRESH_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public String getToken(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_HEADER);
    }

    public String substringToken(String tokenValue) {
        if(tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(BEARER_PREFIX.length());
        }

        //tokenValue가 Bearer로 시작하지 않는 경우
        throw new CustomException(CustomErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            throw new CustomException(CustomErrorCode.SIGNATURE_EXCEPTION);
        } catch (ExpiredJwtException e) {
            throw new CustomException(CustomErrorCode.EXPIRED_ACCESS_TOKEN_EXCEPTION);
        } catch (UnsupportedJwtException e) {
            throw new CustomException(CustomErrorCode.UNSUPPORTED_JWT_EXCEPTION);
        } catch (IllegalArgumentException e) {
            throw new CustomException(CustomErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        }
    }

    public String getSubject(String token) {
        Claims body = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        //return member email
        return body.getSubject();
    }
}
