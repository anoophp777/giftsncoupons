package com.giftsncoupons.cart.application.services.transformer;

import com.giftsncoupons.cart.controller.models.Cart;
import com.giftsncoupons.cart.controller.models.Item;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class CartInfraToCartTransformer implements Function<com.giftsncoupons.cart.infrastructure.cart.models.Cart, Cart> {
    @Override
    public Cart apply(com.giftsncoupons.cart.infrastructure.cart.models.Cart cart) {
        return Cart.builder().userId(cart.getUserId()).totalPrice(cart.getTotalPrice()).items(convertToListOfItems(cart.getItems())).build();
    }

    public List<Item> convertToListOfItems(List<com.giftsncoupons.cart.infrastructure.cart.models.Item> items) {
        List<Item> listOfItems = new ArrayList<>();
        for (com.giftsncoupons.cart.infrastructure.cart.models.Item item : items) {
            listOfItems.add(Item.builder()
                    .itemId(item.getItemId())
                    .price(item.getPrice())
                    .name(item.getName())
                    .quantity(item.getQuantity())
                    .build());
        }
        return listOfItems;
    }
}
