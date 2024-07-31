package com.giftsncoupons.cart.application.services.cart;

import com.giftsncoupons.cart.controller.models.Cart;

import java.util.List;
import java.util.Optional;

public interface CartService {


    List<Cart> getAllCarts();

    Cart updateCart(Cart cart);

    Cart createCart(Cart cart);

    void deleteById(String cartId);

    Optional<Cart> findCartById(String cartId);
}
