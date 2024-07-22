package com.giftsncoupons.cart.domain.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartModel {
    private String userId;
    private List<ItemModel> items;
    private double totalPrice;

}
