package com.spring.rate.limit.domain.redis.service;

import com.spring.rate.limit.domain.cache.model.enums.CacheType;
import com.spring.rate.limit.domain.cache.service.strategy.RedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedisServiceTest {
    private static final String KEY = "key";
    private static final String VALUE = "value";
    private static final Duration EXPIRATION_TIME = Duration.ofSeconds(60);
    @Mock
    private RedisTemplate<String, String> redisTemplate;
    @Mock
    private ValueOperations<String, String> valueOperations;

    private RedisService redisService;

    @BeforeEach
    public void setUp() {
        redisService = new RedisService(redisTemplate);
    }

    @Test
    void testSetWithExpiration() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        redisService.setWithExpiration(KEY, VALUE, EXPIRATION_TIME);

        verify(valueOperations).set(KEY, VALUE, EXPIRATION_TIME);
    }

    @Test
    void testGetFromRedis() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(KEY)).thenReturn(VALUE);

        String returnedValue = redisService.getRecord(KEY);

        verify(valueOperations).get(KEY);
        assertEquals(VALUE, returnedValue);
    }

    @Test
    void canHandleReturnsTrueForRedisType() {
        boolean response = redisService.canHandle(CacheType.REDIS);

        assertTrue(response);
    }
}