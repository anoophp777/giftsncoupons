package com.giftsncoupons.cart.controller.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckoutResponse {

    private String userId;
    private double shippingCost;
    private long deliveryDate;
    private String confirmationId;
    private Cart cart;
}
