package com.giftsncoupons.cart.infrastructure.cart.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@Builder
@UserDefinedType
public class Item {
    private String itemId;
    private int quantity;
    private String name;
    private double price;
}
