package com.giftsncoupons.cart.application.services.transformer;

import com.giftsncoupons.cart.controller.models.Cart;
import com.giftsncoupons.cart.controller.models.CheckoutResponse;
import com.giftsncoupons.cart.infrastructure.order.models.Order;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class OrderToCheckoutTransformer implements BiFunction<Order, Cart, CheckoutResponse> {

    @Override
    public CheckoutResponse apply(Order order, Cart cart) {
        return CheckoutResponse.builder()
                .confirmationId(order.getConfirmationId())
                .deliveryDate(order.getDeliveryDate())
                .shippingCost(order.getShippingCost())
                .userId(order.getUserId())
                .cart(cart)
                .build();
    }
}
