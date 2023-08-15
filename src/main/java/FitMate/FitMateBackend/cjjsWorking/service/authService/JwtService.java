package FitMate.FitMateBackend.cjjsWorking.service.authService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Component
@Slf4j
public class JwtService {


    private static String secretKey;
    private static Long expiration;
    private static String issuer;

    @Value("${jwt-expiration}")
    public void setExpiration(Long ep) {
        this.expiration = ep;
    }
    @Value("${jwt-secret-key}")
    public void setSecretKey(String sk){
        this.secretKey = sk;
    }

    @Value("${jwt-issuer}")
    public void setIssuer(String iss) {
        this.issuer = iss;
    }
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

    public static String getLoginEmail(String token) {
        return getClaim(token, Claims::getSubject);
    }
    public static Long getUserId(String token) {
        return getClaim(token, claims -> claims.get("userId", Long.class));
    }

    public static <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private static Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private static Key getSignInKey() {
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
