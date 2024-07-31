package com.giftsncoupons.cart.application.services.transformer;

import com.giftsncoupons.cart.infrastructure.promotion.models.FreeGift;
import com.giftsncoupons.cart.infrastructure.promotion.models.Promotion;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class PromotionInfraToPromotionTransformer implements Function<Promotion, com.giftsncoupons.cart.controller.models.Promotion> {
    @Override
    public com.giftsncoupons.cart.controller.models.Promotion apply(Promotion promotion) {

        return com.giftsncoupons.cart.controller.models.Promotion.builder().couponCode(promotion.getCouponCode())
                .freeGifts(convertFreeGifts(promotion.getFreeGifts()))
                .startDate(promotion.getStartDate())
                .endDate(promotion.getEndDate())
                .build();
    }

    private List<com.giftsncoupons.cart.controller.models.FreeGift> convertFreeGifts(List<FreeGift> list) {
        List<com.giftsncoupons.cart.controller.models.FreeGift> freeGiftList = new ArrayList<>();

        for (FreeGift freeGift : list) {
            freeGiftList.add(com.giftsncoupons.cart.controller.models.FreeGift.builder()
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
