package com.giftsncoupons.cart.infrastructure.order.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;

@Data
@Table
@Builder
public class Order {

    @PrimaryKey
    private String userId;
    private double shippingCost;
    private Date deliveryDate;
    private String confirmationId;
}
