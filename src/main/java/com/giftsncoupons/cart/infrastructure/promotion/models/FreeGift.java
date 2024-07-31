package com.giftsncoupons.cart.infrastructure.promotion.models;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FreeGift {

    private String giftId;
    private String name;
    private int quantity;
    private long startDate;
    private long endDate;
}
