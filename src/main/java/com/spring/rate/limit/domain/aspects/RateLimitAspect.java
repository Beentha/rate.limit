package com.spring.rate.limit.domain.aspects;

import com.spring.rate.limit.domain.annotations.RateLimiter;
import com.spring.rate.limit.domain.exceptions.RateLimitException;
import com.spring.rate.limit.domain.rate_limit.model.request.RateLimitRequest;
import com.spring.rate.limit.domain.rate_limit.service.RateLimitService;
import com.spring.rate.limit.domain.user_token.service.UserTokenExecutor;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitAspect {

    private final UserTokenExecutor tokenExecutor;
    private final RateLimitService rateLimitService;

    @Before(value = "@annotation(rateLimiter)")
    public void isValid(JoinPoint joinPoint, RateLimiter rateLimiter) {
        String token = tokenExecutor.getToken(rateLimiter.tokenType());
        RateLimitRequest request = new RateLimitRequest(
                rateLimiter.resource(),
                rateLimiter.maximumNumberOfRequests(),
                rateLimiter.durationInMinutes(),
                rateLimiter.cacheType(),
                token
        );

        if (!rateLimitService.isAllowed(request)) {
            throw new RateLimitException(rateLimiter.durationInMinutes());
        }
    }

}
