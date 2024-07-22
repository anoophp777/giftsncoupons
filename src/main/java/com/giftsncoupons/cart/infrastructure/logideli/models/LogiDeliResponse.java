package com.giftsncoupons.cart.infrastructure.logideli.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LogiDeliResponse {
    private double shippingCost;
    private long deliveryDate;
    private String confirmationId;
}
