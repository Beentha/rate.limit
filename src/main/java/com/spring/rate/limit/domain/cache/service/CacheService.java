package com.spring.rate.limit.domain.cache.service;

import com.spring.rate.limit.domain.cache.model.enums.CacheType;

import java.time.Duration;

public interface CacheService {

    void setWithExpiration(String key, String value, Duration expirationTime);

    String getRecord(String key);

    boolean canHandle(CacheType cacheType);
}
