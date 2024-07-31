package com.giftsncoupons.cart.application.services.transformer;

import com.giftsncoupons.cart.domain.models.CartModel;
import com.giftsncoupons.cart.infrastructure.logideli.models.LogiDeliResponse;
import com.giftsncoupons.cart.infrastructure.order.models.Order;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class OrderRequestTransformer implements BiFunction<LogiDeliResponse, CartModel, Order> {
    @Override
    public Order apply(LogiDeliResponse logiDeliResponse, CartModel cart) {
        return Order.builder()
                .userId(cart.getUserId())
                .deliveryDate(logiDeliResponse.getDeliveryDate())
                .shippingCost(logiDeliResponse.getShippingCost())
                .confirmationId(logiDeliResponse.getConfirmationId()).build();
    }
}
