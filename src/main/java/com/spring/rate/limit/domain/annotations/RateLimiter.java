package com.spring.rate.limit.domain.annotations;

import com.spring.rate.limit.domain.cache.model.enums.CacheType;
import com.spring.rate.limit.domain.user_token.model.enums.UserTokenType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RateLimiter {

    int maximumNumberOfRequests() default 5;
    long durationInMinutes() default 1;
    String resource() default "DEFAULT";
    CacheType cacheType() default CacheType.REDIS;
    UserTokenType tokenType() default UserTokenType.IP;

}
