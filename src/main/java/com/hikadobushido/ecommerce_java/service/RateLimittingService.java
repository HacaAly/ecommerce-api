package com.hikadobushido.ecommerce_java.service;

import java.util.function.Supplier;

public interface RateLimittingService {
    <T> T executWitRateLimit(String key, Supplier<T> operation);
}
