package com.hikadobushido.ecommerce_java.config;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimitConfiguration {

    @Value("${rate.limit.default:100}")
    private int defaultLimitForPeriod;

    @Value("${rate.limit.period:60}")
    private int limitRefreshPeriodInSecond;

    @Value("${rate.limit.timeout:1}")
    private int timeOutInSecond;

    @Bean
    public RateLimiterConfig rateLimiterConfig() {
        return RateLimiterConfig.custom()
                .limitForPeriod(defaultLimitForPeriod)
                .limitRefreshPeriod(Duration.ofSeconds(limitRefreshPeriodInSecond))
                .timeoutDuration(Duration.ofSeconds(timeOutInSecond))
                .build();
    }

    @Bean
    public RateLimiterRegistry rateLimiterRegistry(RateLimiterConfig rateLimiterConfig) {
        return RateLimiterRegistry.of(rateLimiterConfig);
    }
}
