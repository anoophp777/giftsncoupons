package com.giftsncoupons.cart.application.transformer;

import com.giftsncoupons.cart.domain.models.FreeGiftModel;
import com.giftsncoupons.cart.domain.models.PromotionModel;
import com.giftsncoupons.cart.infrastructure.promotion.models.FreeGift;
import com.giftsncoupons.cart.infrastructure.promotion.models.Promotion;
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
                .freeGift(convertToFreeGiftModel(promotion.getFreeGift()))
                .build();
    }

    private List<FreeGiftModel> convertToFreeGiftModel(List<FreeGift> freeGifts) {
        List<FreeGiftModel> freeGiftModels = new ArrayList<>();
        for (FreeGift freeGift : freeGifts) {
            freeGiftModels.add(FreeGiftModel.builder()
                    .giftId(freeGift.getGiftId())
                    .quantity(freeGift.getQuantity())
                    .startDate(freeGift.getStartDate())
                    .endDate(freeGift.getEndDate()).build());
        }
        return freeGiftModels;
    }
}
