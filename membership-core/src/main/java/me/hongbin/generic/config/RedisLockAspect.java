package me.hongbin.generic.config;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@Aspect
public class RedisLockAspect {

    private final AopForTransaction aopForTransaction;
    private final RedissonClient redissonClient;

    public RedisLockAspect(AopForTransaction aopForTransaction, RedissonClient redissonClient) {
        this.aopForTransaction = aopForTransaction;
        this.redissonClient = redissonClient;
    }

    @Around("@annotation(me.hongbin.generic.config.RedisLock)")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Object lock(ProceedingJoinPoint joinPoint) throws Throwable {

        var lock = redissonClient.getLock("test");

        var b = lock.tryLock(1000L, 1000L, TimeUnit.SECONDS);
        var proceed = aopForTransaction.proceed(joinPoint);
        return proceed;
    }
}
