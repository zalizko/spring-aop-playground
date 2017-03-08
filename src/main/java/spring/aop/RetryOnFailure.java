package spring.aop;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RetryOnFailure {

    int attempts() default 2;

    long delay() default 1;

    TimeUnit unit() default TimeUnit.SECONDS;
}