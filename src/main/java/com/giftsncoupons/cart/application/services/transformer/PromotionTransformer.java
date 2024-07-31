package com.giftsncoupons.cart.application.services.transformer;

import com.giftsncoupons.cart.controller.models.FreeGift;
import com.giftsncoupons.cart.controller.models.Promotion;
import com.giftsncoupons.cart.domain.models.FreeGiftModel;
import com.giftsncoupons.cart.domain.models.PromotionModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class PromotionTransformer implements Function<Promotion, PromotionModel> {
    @Override
    public PromotionModel apply(Promotion promotion) {
        return PromotionModel.builder()
                .couponCode(promotion.getCouponCode())
                .startDate(promotion.getStartDate())
                .endDate(promotion.getEndDate())
                .freeGift(convertToFreeGiftModel(promotion.getFreeGifts()))
                .build();
    }

    private List<FreeGiftModel> convertToFreeGiftModel(List<FreeGift> freeGifts) {
        List<FreeGiftModel> freeGiftModels = new ArrayList<>();
        for (FreeGift freeGift : freeGifts) {
            freeGiftModels.add(FreeGiftModel.builder()
                    .giftId(freeGift.getGiftId())
                    .name(freeGift.getName())
                    .quantity(freeGift.getQuantity())
                    .startDate(freeGift.getStartDate())
                    .endDate(freeGift.getEndDate()).build());
        }
        return freeGiftModels;
    }
}
