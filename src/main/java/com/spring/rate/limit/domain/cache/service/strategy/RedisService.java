package com.spring.rate.limit.domain.cache.service.strategy;

import com.spring.rate.limit.domain.cache.model.enums.CacheType;
import com.spring.rate.limit.domain.cache.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.spring.rate.limit.domain.cache.model.enums.CacheType.REDIS;

@Service
@RequiredArgsConstructor
public class RedisService implements CacheService {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void setWithExpiration(String key, String value, Duration expirationTime) {
        redisTemplate.opsForValue().set(key, value, expirationTime);
    }

    @Override
    public String getRecord(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean canHandle(CacheType cacheType) {
        return REDIS.equals(cacheType);
    }
}