package me.hongbin.generic.config;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class RetryAspect {

    @Around("@annotation(me.hongbin.generic.config.Retry)")
    public Object retry(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        var method = signature.getMethod();
        var retry = method.getDeclaredAnnotation(Retry.class);

        for (int i = 0; i < retry.count(); i++) {
            try {
                return joinPoint.proceed();
            } catch (Exception e) {
                var ignoreClasses = retry.ignoreExceptions();
                var canRetry = canRetry(e, ignoreClasses);

                if (!canRetry) {
                    throw e;
                }
            }
        }
        throw new RetryExceededException();
    }

    private static boolean canRetry(Exception exception, Class<? extends Exception>[] classes) {
        Class<? extends Exception> exceptionClass = exception.getClass();

        return Arrays.stream(classes)
            .anyMatch(clazz -> clazz.isAssignableFrom(exceptionClass));
    }
}
