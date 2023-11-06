package me.hongbin.generic;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import me.hongbin.generic.exception.LockConflictException;
import me.hongbin.generic.type.LockSupport;

@Component
public class RedisLockSupport implements LockSupport {

    private static final String LOCK_USER_ID_FORMAT = "lock:user-id:%s";
    private static final int DEFAULT_TIMEOUT = 10;

    private final StringRedisTemplate redisTemplate;

    public RedisLockSupport(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void getLock(String key) {
        var operations = redisTemplate.opsForValue();
        var lockKey = LOCK_USER_ID_FORMAT.formatted(key);

        var count = operations.increment(lockKey);
        redisTemplate.expire(lockKey, DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        if (!Objects.equals(count, 1L)) {
            operations.decrement(lockKey);
            throw new LockConflictException("User 락 획득 실패 userId = %s".formatted(key));
        }
    }

    public void release(String key) {
        var operations = redisTemplate.opsForValue();
        var lockKey = LOCK_USER_ID_FORMAT.formatted(key);

        operations.decrement(lockKey);
    }
}
