package com.giftsncoupons.cart.application.services.admin;

import com.giftsncoupons.cart.application.services.promotion.PromotionServiceImpl;
import com.giftsncoupons.cart.controller.models.Item;
import com.giftsncoupons.cart.controller.models.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final PromotionServiceImpl promotionService;

    @Autowired
    public AdminServiceImpl(PromotionServiceImpl promotionService) {
        this.promotionService = promotionService;
    }

    @Override
    public List<Item> findAllItems() {
        Item pressureCooker = Item.builder().itemId("1").name("PressureCooker").price(1500).build();
        Item blender = Item.builder().itemId("2").name("Blender").price(1000).build();
        Item coffeePot = Item.builder().itemId("3").name("CoffeePot").price(500).build();
        return List.of(pressureCooker, blender, coffeePot);
    }

    @Override
    public Promotion savePromotion(Promotion promotion) {
        return promotionService.savePromotion(promotion);
    }

    @Override
    public Promotion findPromotion(String coupon) {
        return promotionService.findPromotion(coupon);

    }

    @Override
    public List<Promotion> findAllPromotions() {
        return promotionService.findAllPromotions();
    }
}
