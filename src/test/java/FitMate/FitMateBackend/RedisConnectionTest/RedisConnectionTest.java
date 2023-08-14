package FitMate.FitMateBackend.RedisConnectionTest;

import FitMate.FitMateBackend.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class RedisConnectionTest {

    @Autowired private RedisTemplate<String, User> userRedisTemplate;

    @Test
    public void redisConnectionTest() {
        //given
        ValueOperations<String, User> valueOperations = userRedisTemplate.opsForValue();
        User user = new User();
        String token = "token";

        //when
        valueOperations.set(token, user);

        //then
        System.out.println(valueOperations.get(token));
    }
}
