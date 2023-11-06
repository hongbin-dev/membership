package me.hongbin.generic.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AopForTransaction {

    // 이게 새 트랜잭션이 생김
    // 비즈니스코드를 새 트랜잭션으로 마무리

    //  TODO 이럴떈 타임아웃시..?
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Object proceed(final ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("############## start");
        var joinPoint1 = joinPoint;
        System.out.println("############## end");
        return joinPoint1.proceed();
    }
}