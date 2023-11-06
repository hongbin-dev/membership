package me.hongbin.barcode.infra;

import java.util.random.RandomGenerator;

import org.springframework.stereotype.Component;

import me.hongbin.barcode.domain.NumberGenerator;

@Component
public class RandomNumberGenerator implements NumberGenerator {

    private final RandomGenerator randomGenerator = RandomGenerator.getDefault();

    @Override
    public Long generate() {
        return randomGenerator.nextLong(1, 9999999999L);
    }
}
