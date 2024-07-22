package com.giftsncoupons.cart.controller.checkout;

import com.giftsncoupons.cart.application.services.CheckoutService;
import com.giftsncoupons.cart.controller.checkout.models.CheckoutResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @Autowired
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping
    @RequestMapping("/{cartId}")
    public CheckoutResponse checkoutCart(@PathVariable("cartId") String cartId) {
        return checkoutService.checkout(cartId);
    }

}
