package com.giftsncoupons.cart.controller.models;


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
