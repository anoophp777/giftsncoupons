package com.giftsncoupons.cart.controller;

import com.giftsncoupons.cart.application.services.CartService;
import com.giftsncoupons.cart.infrastructure.cart.models.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<Cart> getAllCart(){
        return cartService.getAllCarts();
    }

    @PutMapping
    public  Cart updateCart(@RequestBody Cart cart){
        return cartService.updateCart(cart);
    }

    @PostMapping
    public  Cart createCart(@RequestBody Cart cart){
        return cartService.createCart(cart);
    }


}
