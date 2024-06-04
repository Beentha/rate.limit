package com.spring.rate.limit.domain.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateLimitException extends RuntimeException {

    private final long retryAfterSeconds;

    public RateLimitException(long retryAfterSeconds) {
        this.retryAfterSeconds = retryAfterSeconds;
    }

}
