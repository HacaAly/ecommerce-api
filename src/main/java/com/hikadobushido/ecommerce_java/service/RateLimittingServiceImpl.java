package com.hikadobushido.ecommerce_java.service;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@RequiredArgsConstructor
@Slf4j
@Service
public class RateLimittingServiceImpl implements RateLimittingService {

    private final RateLimiterRegistry rateLimiterRegistry;

    @Override
    public <T> T executWitRateLimit(String key, Supplier<T> operation) {
        RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter(key);
        return RateLimiter.decorateSupplier(rateLimiter, operation).get();
    }
}
