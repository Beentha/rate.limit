package com.spring.rate.limit.domain.rate_limit.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RateLimitModel {

    private int requestCount;

    private long expiration;
}
