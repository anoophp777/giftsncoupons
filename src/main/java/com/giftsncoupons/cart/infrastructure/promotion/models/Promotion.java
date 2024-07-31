package com.giftsncoupons.cart.infrastructure.promotion.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Data
@RedisHash
@Builder
public class Promotion {

    @Id
    private String couponCode;
    private List<FreeGift> freeGifts;
    private long startDate;
    private long endDate;
}
