package com.giftsncoupons.cart.infrastructure.logideli.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
public class LogiDeliResponse {
    private double shippingCost;
    private Date deliveryDate;
    private String confirmationId;
}
