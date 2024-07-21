package com.giftsncoupons.cart.domain.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemModel {

    private String itemId;
    private int quantity;
    private String name;
    private double price;
}
