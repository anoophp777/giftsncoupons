package com.giftsncoupons.cart.application.transformer;

import com.giftsncoupons.cart.domain.models.CartModel;
import com.giftsncoupons.cart.domain.models.ItemModel;
import com.giftsncoupons.cart.infrastructure.cart.models.Cart;
import com.giftsncoupons.cart.infrastructure.cart.models.Item;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class CartTransformer implements Function<Cart, CartModel> {
    @Override
    public CartModel apply(Cart cart) {
        return CartModel.builder()
                .userId(cart.getUserId())
                .totalPrice(cart.getTotalPrice())
                .items(convertToItemModels(cart.getItems()))
                .build();
    }

    private List<ItemModel> convertToItemModels(List<Item> items) {
        List<ItemModel> itemModels = new ArrayList<>();
        for (Item item : items) {
            itemModels.add(ItemModel.builder()
                    .itemId(item.getItemId())
                    .name(item.getName())
                    .price(item.getPrice())
                    .quantity(item.getQuantity())
                    .build());
        }
        return itemModels;
    }
}
