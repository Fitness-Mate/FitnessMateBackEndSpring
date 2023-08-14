package FitMate.FitMateBackend.cjjsWorking.service.authService;

import FitMate.FitMateBackend.cjjsWorking.exception.CustomErrorCode;
import FitMate.FitMateBackend.cjjsWorking.exception.CustomException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt-secret-key}") private String secretKey;
    @Value("${jwt-expiration}") private Long expiration;
    @Value("${jwt-issuer}") private String issuer;

    public String generateToken(UserDetails userDetails, ExtraClaims claims) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", claims.getUserId());

        return generateToken(extraClaims, userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuer(issuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getToken(HttpHeaders header) {
        return Objects.requireNonNull(header.getFirst("authorization")).substring("Bearer ".length());
    }

    public String getLoginEmail(String token) {
        return getClaim(token, Claims::getSubject);
    }
    public Long getUserId(String token) {
        return getClaim(token, claims -> claims.get("userId", Long.class));
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch(ExpiredJwtException e) {
            throw new CustomException(CustomErrorCode.EXPIRED_JWT_EXCEPTION);
        } catch(UnsupportedJwtException e) {
            throw new CustomException(CustomErrorCode.UNSUPPORTED_JWT_EXCEPTION);
        } catch(MalformedJwtException e) {
            throw new CustomException(CustomErrorCode.MALFORMED_JWT_EXCEPTION);
        } catch(SignatureException e) {
            throw new CustomException(CustomErrorCode.SIGNATURE_EXCEPTION);
        } catch(IllegalArgumentException e) {
            throw new CustomException(CustomErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        }
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String loginId = getLoginEmail(token);
        return (loginId.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }
}
