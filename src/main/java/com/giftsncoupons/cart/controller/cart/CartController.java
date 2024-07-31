package com.giftsncoupons.cart.controller.cart;

import com.giftsncoupons.cart.application.services.cart.CartServiceImpl;
import com.giftsncoupons.cart.controller.models.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartServiceImpl cartService;

    @Autowired
    public CartController(CartServiceImpl cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<Cart> getAllCart() {
        return cartService.getAllCarts();
    }

    @PutMapping
    public Cart updateCart(@RequestBody Cart cart) {
        return cartService.updateCart(cart);
    }

    @PostMapping
    public Cart createCart(@RequestBody Cart cart) {
        return cartService.createCart(cart);
    }


}
