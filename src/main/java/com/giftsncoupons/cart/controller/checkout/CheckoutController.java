package com.giftsncoupons.cart.controller.checkout;

import com.giftsncoupons.cart.application.services.checkout.CheckoutServiceImpl;
import com.giftsncoupons.cart.controller.models.CheckoutResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutServiceImpl checkoutService;

    @Autowired
    public CheckoutController(CheckoutServiceImpl checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping
    @RequestMapping("/{cartId}")
    public CheckoutResponse checkoutCart(@PathVariable("cartId") String cartId) {
        return checkoutService.checkout(cartId);
    }

}
