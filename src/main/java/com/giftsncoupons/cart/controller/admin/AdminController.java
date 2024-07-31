package com.giftsncoupons.cart.controller.admin;

import com.giftsncoupons.cart.application.services.admin.AdminService;
import com.giftsncoupons.cart.controller.models.Item;
import com.giftsncoupons.cart.controller.models.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/items")
    public List<Item> findAllItems() {
        return adminService.findAllItems();
    }

    @PostMapping("/promos")
    @ResponseStatus(HttpStatus.CREATED)
    public Promotion addPromotion(@RequestBody Promotion promotion) {
        return adminService.savePromotion(promotion);
    }

    @GetMapping("/promos/{coupon}")
    public Promotion getPromotion(@PathVariable("coupon") String coupon) {
        return adminService.findPromotion(coupon);
    }

    @GetMapping("/promos")
    public List<Promotion> getPromotion() {
        return adminService.findAllPromotions();
    }
}
