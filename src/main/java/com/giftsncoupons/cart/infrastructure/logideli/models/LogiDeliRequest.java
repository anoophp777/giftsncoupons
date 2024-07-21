package com.giftsncoupons.cart.infrastructure.logideli.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LogiDeliRequest {

    private String name;
    private Address address;
    private String email;
    private List<LogiDeliItem> items;
    private Address pickupLocation;
}
