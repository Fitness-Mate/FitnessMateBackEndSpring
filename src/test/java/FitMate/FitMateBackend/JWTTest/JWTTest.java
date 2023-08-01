package FitMate.FitMateBackend.JWTTest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Date;

@SpringBootTest
@Transactional
public class JWTTest {
    /**
     * JWT implementation reference:
     * https://shinsunyoung.tistory.com/110 - without Spring Security
     * https://colabear754.tistory.com/171 -with Spring Security
     * */

    @Test
    public void makeJWTTest() {
        Date now = new Date();

        String token = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("fitmate") //Token 발행자
                .setIssuedAt(now) //Token 발행일자
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis())) //Token 만료시간설정
                .claim("name", "zzzzseong") //비공개 Claim - Token에 담을 정보
                .claim("email", "jung48182@gmail.com") //비공개 Claim - Token에 담을 정보
                .signWith(SignatureAlgorithm.HS256, "secret") //해싱 알고리즘 및 시크릿 키 설정
                .compact();

        System.out.println(token);

        Claims claims = parseJWT(token);
        System.out.println(claims.get("name"));
    }

    public Claims parseJWT(String authorizationHeader) {
//        validationAuthorizationHeader(authorizationHeader);
//        String token = extractToken(authorizationHeader);

        return Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(authorizationHeader)
                .getBody();
    }
    private void validationAuthorizationHeader(String header) {
        //Bearer 로 시작하는지 확인
        if(header == null || !header.startsWith("Bearer ")) {
            throw new IllegalArgumentException();
        }
    }
    private String extractToken(String authorizationHeader) {
        //Bearer 다음 부터 있는 token 추출
        return authorizationHeader.substring("Bearer ".length());
    }

}
