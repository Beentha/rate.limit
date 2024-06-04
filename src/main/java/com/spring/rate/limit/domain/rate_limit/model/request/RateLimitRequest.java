package com.spring.rate.limit.domain.rate_limit.model.request;

import com.spring.rate.limit.domain.cache.model.enums.CacheType;

public record RateLimitRequest(String key,
                               int maximumNumberOfRequests,
                               long duration,
                               CacheType cacheType,
                               String userToken) {
}
