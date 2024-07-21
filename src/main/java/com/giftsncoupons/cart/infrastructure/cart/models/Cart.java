package com.giftsncoupons.cart.infrastructure.cart.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;


@Data
@Table
@Builder
public class Cart {

    @PrimaryKey
    private String userId;
    private List<Item> items;
    private double totalPrice;

}
