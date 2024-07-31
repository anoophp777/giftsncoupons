package com.giftsncoupons.cart.application.services.transformer;

import com.giftsncoupons.cart.controller.models.Cart;
import com.giftsncoupons.cart.controller.models.Item;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class CartToCartInfraTransformer implements Function<Cart, com.giftsncoupons.cart.infrastructure.cart.models.Cart> {
    @Override
    public com.giftsncoupons.cart.infrastructure.cart.models.Cart apply(Cart cart) {
        return com.giftsncoupons.cart.infrastructure.cart.models.Cart.builder()
                .userId(cart.getUserId())
                .totalPrice(cart.getTotalPrice())
                .items(convertToListOfItems(cart.getItems()))
                .build();
    }

    public List<com.giftsncoupons.cart.infrastructure.cart.models.Item> convertToListOfItems(List<Item> items) {
        List<com.giftsncoupons.cart.infrastructure.cart.models.Item> listOfItems = new ArrayList<>();
        for (Item item : items) {
            listOfItems.add(com.giftsncoupons.cart.infrastructure.cart.models.Item.builder()
                    .itemId(item.getItemId())
                    .price(item.getPrice())
                    .name(item.getName())
                    .quantity(item.getQuantity())
                    .build());
        }
        return listOfItems;
    }
}
