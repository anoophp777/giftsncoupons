package com.giftsncoupons.cart.controller;

import com.giftsncoupons.cart.application.services.PromotionService;
import com.giftsncoupons.cart.infrastructure.promotion.models.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    private PromotionService promotionService;
    private RedisTemplate<String, String> template;

    @Autowired
    public AdminController(PromotionService promotionService, RedisTemplate<String, String> template) {
        this.promotionService = promotionService;
        this.template = template;
    }

    @PostMapping("/promos")
    @ResponseStatus(HttpStatus.CREATED)
    public Promotion addPromotion(@RequestBody Promotion promotion) {
        return promotionService.savePromotion(promotion);
    }

    @GetMapping("/promos/{coupon}")
    public Promotion getPromotion(@PathVariable("coupon") String coupon) {
        return promotionService.findPromotion(coupon);
    }
}
