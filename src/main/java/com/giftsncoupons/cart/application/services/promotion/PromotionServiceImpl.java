package com.giftsncoupons.cart.application.services.promotion;

import com.giftsncoupons.cart.application.services.transformer.PromotionInfraToPromotionTransformer;
import com.giftsncoupons.cart.application.services.transformer.PromotionToPromotionInfraTransformer;
import com.giftsncoupons.cart.controller.models.Promotion;
import com.giftsncoupons.cart.infrastructure.promotion.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final PromotionInfraToPromotionTransformer promotionInfraToPromotionTransformer;
    private final PromotionToPromotionInfraTransformer promotionToPromotionInfraTransformer;

    @Autowired
    public PromotionServiceImpl(PromotionRepository promotionRepository,
                                PromotionInfraToPromotionTransformer promotionInfraToPromotionTransformer,
                                PromotionToPromotionInfraTransformer promotionToPromotionInfraTransformer) {
        this.promotionRepository = promotionRepository;
        this.promotionInfraToPromotionTransformer = promotionInfraToPromotionTransformer;
        this.promotionToPromotionInfraTransformer = promotionToPromotionInfraTransformer;
    }

    /**
     * @param couponCode
     * @return
     */
    @Override
    public Promotion findPromotion(String couponCode) {
        return promotionInfraToPromotionTransformer.apply(promotionRepository.findById(couponCode).orElseThrow());
    }

    @Override
    public List<Promotion> findAllPromotions() {
        List<Promotion> promotions = new ArrayList<>();
        for (com.giftsncoupons.cart.infrastructure.promotion.models.Promotion promotion : promotionRepository.findAll()) {
            promotions.add(promotionInfraToPromotionTransformer.apply(promotion));
        }
        return promotions;
    }

    /**
     * @param promotion
     * @return
     */
    @Override
    public Promotion savePromotion(Promotion promotion) {
        return promotionInfraToPromotionTransformer
                .apply(promotionRepository.save(promotionToPromotionInfraTransformer.apply(promotion)));
    }
}
