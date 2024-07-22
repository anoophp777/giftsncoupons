package com.giftsncoupons.cart.controller.cart.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {
    private String itemId;
    private int quantity;
    private String name;
    private double price;
}