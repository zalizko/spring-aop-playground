package spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public final class MethodRepeater {

    @Around("execution(* spring.aop..*(..)) && @annotation(RetryOnFailure)")
    public Object wrap(final ProceedingJoinPoint joinPoint) throws Throwable {
        final Method method = MethodSignature.class.cast(joinPoint.getSignature()).getMethod();
        final RetryOnFailure retryOnFailure = method.getAnnotation(RetryOnFailure.class);
        return proceed(joinPoint, retryOnFailure);
    }

    private Object proceed(ProceedingJoinPoint joinPoint, RetryOnFailure retryOnFailure) throws Throwable {
        int attempt = 0;

        while (true) {
            try {
                return joinPoint.proceed();
            } catch (final Throwable ex) {
                if (++attempt >= retryOnFailure.attempts()) {
                    throw ex;
                }
                if (retryOnFailure.delay() > 0L) {
                    retryOnFailure.unit().sleep(retryOnFailure.delay());
                }
            }
        }
    }
}