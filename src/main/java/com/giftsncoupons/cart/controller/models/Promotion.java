package com.giftsncoupons.cart.controller.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Promotion {

    private String couponCode;
    private List<FreeGift> freeGifts;
    private long startDate;
    private long endDate;
}
