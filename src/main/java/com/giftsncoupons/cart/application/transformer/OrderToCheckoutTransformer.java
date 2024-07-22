package com.giftsncoupons.cart.application.transformer;

import com.giftsncoupons.cart.controller.checkout.models.CheckoutResponse;
import com.giftsncoupons.cart.infrastructure.order.models.Order;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class OrderToCheckoutTransformer implements Function<Order, CheckoutResponse> {
    @Override
    public CheckoutResponse apply(Order order) {
        return CheckoutResponse.builder()
                .confirmationId(order.getConfirmationId())
                .deliveryDate(order.getDeliveryDate())
                .shippingCost(order.getShippingCost())
                .userId(order.getUserId())
                .build();
    }
}
