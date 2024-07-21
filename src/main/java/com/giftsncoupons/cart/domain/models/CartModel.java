package com.giftsncoupons.cart.domain.models;

import com.giftsncoupons.cart.infrastructure.cart.models.Item;
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
