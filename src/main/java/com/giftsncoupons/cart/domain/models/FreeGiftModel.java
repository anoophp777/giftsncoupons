package com.giftsncoupons.cart.domain.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FreeGiftModel {

    private String giftId;
    private int quantity;
    private long startDate;
    private long endDate;
}
