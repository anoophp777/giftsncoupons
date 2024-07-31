package com.giftsncoupons.cart.application.services.promotion;

import com.giftsncoupons.cart.controller.models.Promotion;

import java.util.List;

public interface PromotionService {
    Promotion findPromotion(String couponCode);

    List<Promotion> findAllPromotions();

    Promotion savePromotion(Promotion promotion);
}
