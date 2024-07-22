package com.giftsncoupons.cart.application.services;

import com.giftsncoupons.cart.application.transformer.CartInfraToCartTransformer;
import com.giftsncoupons.cart.application.transformer.CartToCartInfraTransformer;
import com.giftsncoupons.cart.controller.cart.models.Cart;
import com.giftsncoupons.cart.infrastructure.cart.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartInfraToCartTransformer cartInfraToCartTransformer;
    private final CartToCartInfraTransformer cartToCartInfraTransformer;

    @Autowired
    public CartService(CartRepository cartRepository, CartInfraToCartTransformer cartInfraToCartTransformer,
                       CartToCartInfraTransformer cartToCartInfraTransformer) {
        this.cartRepository = cartRepository;
        this.cartInfraToCartTransformer = cartInfraToCartTransformer;
        this.cartToCartInfraTransformer = cartToCartInfraTransformer;
    }

    public List<Cart> getAllCarts() {
        List<Cart> listOfCarts = new ArrayList<>();
        for (com.giftsncoupons.cart.infrastructure.cart.models.Cart cart : cartRepository.findAll()) {
            listOfCarts.add(cartInfraToCartTransformer.apply(cart));
        }
        return listOfCarts;
    }

    public Cart updateCart(Cart cart) {
        com.giftsncoupons.cart.infrastructure.cart.models.Cart cartInfra = cartRepository
                .save(cartToCartInfraTransformer.apply(cart));
        return cartInfraToCartTransformer.apply(cartInfra);
    }

    public Cart createCart(Cart cart) {
        com.giftsncoupons.cart.infrastructure.cart.models.Cart cartInfra = cartRepository
                .save(cartToCartInfraTransformer.apply(cart));
        return cartInfraToCartTransformer.apply(cartInfra);
    }

    public void deleteCart(Cart cart) {
        cartRepository.deleteById(cart.getUserId());
    }

    public Optional<com.giftsncoupons.cart.infrastructure.cart.models.Cart> findById(String cartId) {
        return cartRepository.findById(cartId);
    }
}
