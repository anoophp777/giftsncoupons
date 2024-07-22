package com.giftsncoupons.cart.infrastructure.order.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Table
@Builder
public class Order {

    @PrimaryKey
    private String userId;
    private double shippingCost;
    private long deliveryDate;
    private String confirmationId;
}
