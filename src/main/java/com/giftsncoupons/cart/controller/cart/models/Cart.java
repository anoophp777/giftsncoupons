package com.giftsncoupons.cart.controller.cart.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class Cart {

    private String userId;
    private List<Item> items;
    private double totalPrice;
}
