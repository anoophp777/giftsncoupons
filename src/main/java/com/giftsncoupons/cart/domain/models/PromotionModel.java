package com.giftsncoupons.cart.domain.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PromotionModel {

    private String couponCode;
    private List<FreeGiftModel> freeGift;
    private long startDate;
    private long endDate;
}
