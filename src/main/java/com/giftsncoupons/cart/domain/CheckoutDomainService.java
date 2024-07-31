package com.giftsncoupons.cart.domain;

import com.giftsncoupons.cart.domain.models.CartModel;
import com.giftsncoupons.cart.domain.models.FreeGiftModel;
import com.giftsncoupons.cart.domain.models.ItemModel;
import com.giftsncoupons.cart.domain.models.PromotionModel;
import com.giftsncoupons.cart.infrastructure.logideli.models.LogiDeliItem;
import com.giftsncoupons.cart.infrastructure.logideli.models.LogiDeliRequest;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class CheckoutDomainService {

    public CartModel addFreeGiftPromotion(CartModel cartModel, PromotionModel freeGiftPromotion) {

        Instant currentInstant = Instant.now();
        Instant promotionStartDate = Instant.ofEpochMilli(freeGiftPromotion.getStartDate());
        Instant promotionEndDate = Instant.ofEpochMilli(freeGiftPromotion.getEndDate());

        if (promotionStartDate.isBefore(currentInstant) && promotionEndDate.isAfter(currentInstant)) {
            for (FreeGiftModel freeGift : freeGiftPromotion.getFreeGift()) {
                Instant freeGiftStartDate = Instant.ofEpochMilli(freeGift.getStartDate());
                Instant freeGiftEndDate = Instant.ofEpochMilli(freeGift.getEndDate());
                if (freeGiftStartDate.isBefore(currentInstant) && freeGiftEndDate.isAfter(currentInstant)) {
                    cartModel.getItems().add(ItemModel.builder().itemId(freeGift.getGiftId())
                            .quantity(freeGift.getQuantity())
                                    .name(freeGift.getName())
                            .price(0)
                            .build());
                }
            }
        }
        return cartModel;
    }

    public LogiDeliRequest convertToLogiDeliRequest(CartModel cart) {

        List<LogiDeliItem> logiDeliItems = new ArrayList<>();
        for (ItemModel item : cart.getItems()) {
            LogiDeliItem logiDeliItem = new LogiDeliItem();
            logiDeliItem.setItemId(item.getItemId());
            logiDeliItem.setQuantity(item.getQuantity());
            logiDeliItems.add(logiDeliItem);
        }
        LogiDeliRequest logiDeliRequest = LogiDeliRequest.builder().items(logiDeliItems).build();

        return logiDeliRequest;
    }


}
