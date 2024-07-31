package com.giftsncoupons.cart.application.services.transformer;

import com.giftsncoupons.cart.infrastructure.promotion.models.FreeGift;
import com.giftsncoupons.cart.infrastructure.promotion.models.Promotion;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class PromotionToPromotionInfraTransformer implements Function<com.giftsncoupons.cart.controller.models.Promotion, Promotion> {
    @Override
    public Promotion apply(com.giftsncoupons.cart.controller.models.Promotion promotion) {
        return Promotion.builder().couponCode(promotion.getCouponCode())
                .freeGifts(convertFreeGifts(promotion.getFreeGifts()))
                .startDate(promotion.getStartDate())
                .endDate(promotion.getEndDate())
                .build();
    }

    private List<FreeGift> convertFreeGifts(List<com.giftsncoupons.cart.controller.models.FreeGift> list) {
        List<FreeGift> freeGiftList = new ArrayList<>();

        for(com.giftsncoupons.cart.controller.models.FreeGift freeGift: list) {
            freeGiftList.add(FreeGift.builder()
                    .giftId(freeGift.getGiftId())
                    .name(freeGift.getName())
                    .quantity(freeGift.getQuantity())
                    .endDate(freeGift.getEndDate())
                    .startDate(freeGift.getStartDate())
                    .build());
        }
        return freeGiftList;
    }
}
