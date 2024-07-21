package com.giftsncoupons.cart.application.services;

import com.giftsncoupons.cart.infrastructure.promotion.models.Promotion;
import com.giftsncoupons.cart.infrastructure.promotion.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionService {

    private PromotionRepository promotionRepository;

    @Autowired
    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public Promotion findPromotion(String couponCode) {
        return promotionRepository.findById(couponCode).orElseThrow();
    }

    public Promotion savePromotion(Promotion promotion){
        return promotionRepository.save(promotion);
    }
}
