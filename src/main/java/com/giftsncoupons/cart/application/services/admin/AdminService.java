package com.giftsncoupons.cart.application.services.admin;


import com.giftsncoupons.cart.controller.models.Item;
import com.giftsncoupons.cart.controller.models.Promotion;

import java.util.List;

/**
 *
 */
public interface AdminService {

    List<Item> findAllItems();

    Promotion savePromotion(Promotion promotion);

    Promotion findPromotion(String coupon);

    List<Promotion> findAllPromotions();
}
