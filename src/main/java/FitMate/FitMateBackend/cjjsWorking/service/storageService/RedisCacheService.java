package FitMate.FitMateBackend.cjjsWorking.service.storageService;

import FitMate.FitMateBackend.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RedisCacheService {

    private final RedisTemplate<String, String> userRedisTemplate;
    @Value("${jwt-access-expiration}") private Long accessExp;
    @Value("${jwt-refresh-expiration}") private Long refreshExp;

    @Transactional
    public void saveToken(String refreshToken, boolean rememberMe) {
        long exp = rememberMe ? refreshExp : accessExp;
        ValueOperations<String, String> valueOperations = userRedisTemplate.opsForValue();
        valueOperations.set(refreshToken, refreshToken); //value 값 필요 X
        userRedisTemplate.expire(refreshToken, exp, TimeUnit.MINUTES);

        String re = valueOperations.get("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqczk5MTAwMUBuYXZlci5jb20iLCJpc3MiOiJmaXRtYXRlIiwiaWF0IjoxNjk2NzY3NjYzLCJleHAiOjE2OTc5NzcyNjN9.fXZ3KvXBrrOt0q5ZdlHWqFNsXmf2seOYZ8G5xd1_TfE");
        System.out.println("test: " + re);
    }

    public Boolean isExist(String refreshToken) {
        return userRedisTemplate.hasKey(refreshToken);
    }

    public void removeToken(String refreshToken) {
        userRedisTemplate.delete(refreshToken);
    }
}
