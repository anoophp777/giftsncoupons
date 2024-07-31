package com.giftsncoupons.cart.application.services.transformer;

import com.giftsncoupons.cart.controller.models.Cart;
import com.giftsncoupons.cart.controller.models.Item;
import com.giftsncoupons.cart.domain.models.CartModel;
import com.giftsncoupons.cart.domain.models.ItemModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class CartModelToCartTransformer implements Function<CartModel, Cart> {
    @Override
    public Cart apply(CartModel cart) {
        return Cart.builder()
                .userId(cart.getUserId())
                .totalPrice(cart.getTotalPrice())
                .items(convertToItemModels(cart.getItems()))
                .build();
    }

    private List<Item> convertToItemModels(List<ItemModel> items) {
        List<Item> itemModels = new ArrayList<>();
        for (ItemModel item : items) {
            itemModels.add(Item.builder()
                    .itemId(item.getItemId())
                    .name(item.getName())
                    .price(item.getPrice())
                    .quantity(item.getQuantity())
                    .build());
        }
        return itemModels;
    }
}
