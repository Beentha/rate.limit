package com.spring.rate.limit.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "redis")
@Data
public class RedisProperties {
    private String host;
    private int port;
    private String password;
}