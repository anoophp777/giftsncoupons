package com.giftsncoupons.cart.application.services.checkout;

import com.giftsncoupons.cart.controller.models.CheckoutResponse;

public interface CheckoutService {
    CheckoutResponse checkout(String cartId);
}
