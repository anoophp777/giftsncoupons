package com.giftsncoupons.cart.domain.models;

import com.giftsncoupons.cart.infrastructure.promotion.models.FreeGift;
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
