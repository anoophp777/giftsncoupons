package com.giftsncoupons.cart.application.services.counter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class CounterService {

    @Autowired
    private RedisAtomicInteger redisAtomicInteger;

    public int incrementCounter() {
        return redisAtomicInteger.incrementAndGet();
    }

    public int getCounter() {
        return redisAtomicInteger.get();
    }

    public void resetCounter() {
        redisAtomicInteger.set(0);
    }
}
