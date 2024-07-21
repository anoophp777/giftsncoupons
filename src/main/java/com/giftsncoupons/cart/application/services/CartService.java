package com.giftsncoupons.cart.application.services;

import com.giftsncoupons.cart.infrastructure.cart.models.Cart;
import com.giftsncoupons.cart.infrastructure.cart.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> getAllCarts(){
        return cartRepository.findAll();
    }

    public Cart updateCart(Cart cart){
        return cartRepository.save(cart);
    }

    public Cart createCart(Cart cart){
        return cartRepository.save(cart);
    }

    public void deleteCart(Cart cart){
        cartRepository.delete(cart);
    }

    public Optional<Cart> findById(String cartId){
        return cartRepository.findById(cartId);
    }
}
