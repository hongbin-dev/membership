package me.hongbin.barcode.infra;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import me.hongbin.barcode.domain.BarcodeRepository;

@Repository
public class RedisBarcodeRepository implements BarcodeRepository {

    private static final String POINT_USER_KEY_FORMAT = "point:user-key:%d";
    private static final String POINT_BARCODE_KEY_FORMAT = "point:barcode-key:%s";

    private final StringRedisTemplate redisTemplate;

    @Value("${domain.barcode-expiration-time}")
    private Long barcodeExpirationTime;

    public RedisBarcodeRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Optional<String> findByUserId(Long userId) {
        var value = redisTemplate.opsForValue()
            .get(POINT_USER_KEY_FORMAT.formatted(userId));

        return Optional.ofNullable(value);
    }

    @Override
    public void save(Long userId, String barcode) {
        var operations = redisTemplate.opsForValue();

        operations.set(POINT_USER_KEY_FORMAT.formatted(userId), barcode, barcodeExpirationTime, TimeUnit.MINUTES);
        operations.set(POINT_BARCODE_KEY_FORMAT.formatted(barcode), userId.toString(), barcodeExpirationTime,
            TimeUnit.MINUTES);
    }

    @Override
    public Optional<Long> findByBarcode(String barcode) {
        var value = redisTemplate.opsForValue()
            .get(POINT_BARCODE_KEY_FORMAT.formatted(barcode));

        return Optional.ofNullable(value)
            .map(Long::parseLong);
    }
}
