package com.spring.rate.limit.domain.cache.model.request;

import com.spring.rate.limit.domain.cache.model.enums.CacheType;

import java.time.Duration;

public record CacheRequest(CacheType type,
                           String key,
                           String value,
                           Duration expirationTime) {
}
