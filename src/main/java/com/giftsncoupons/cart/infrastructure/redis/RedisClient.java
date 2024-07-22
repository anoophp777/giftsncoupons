package com.giftsncoupons.cart.infrastructure.redis;

import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Component;

@Component
public class RedisClient {

    private final RedisAtomicInteger redisAtomicInteger;

    public RedisClient(RedisAtomicInteger redisAtomicInteger) {
        this.redisAtomicInteger = redisAtomicInteger;
    }

    public int getAtomicInteger() {
        return redisAtomicInteger.get();
    }

    public int decrementAndGet() {
        return redisAtomicInteger.decrementAndGet();
    }
}
