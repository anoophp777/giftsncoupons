package com.giftsncoupons.cart.application.services.cart;

import com.giftsncoupons.cart.application.services.transformer.CartInfraToCartTransformer;
import com.giftsncoupons.cart.application.services.transformer.CartToCartInfraTransformer;
import com.giftsncoupons.cart.controller.models.Cart;
import com.giftsncoupons.cart.infrastructure.cart.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 */
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartInfraToCartTransformer cartInfraToCartTransformer;
    private final CartToCartInfraTransformer cartToCartInfraTransformer;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CartInfraToCartTransformer cartInfraToCartTransformer,
                           CartToCartInfraTransformer cartToCartInfraTransformer) {
        this.cartRepository = cartRepository;
        this.cartInfraToCartTransformer = cartInfraToCartTransformer;
        this.cartToCartInfraTransformer = cartToCartInfraTransformer;
    }

    /**
     * @return
     */
    @Override
    public List<Cart> getAllCarts() {
        List<Cart> listOfCarts = new ArrayList<>();
        for (com.giftsncoupons.cart.infrastructure.cart.models.Cart cart : cartRepository.findAll()) {
            listOfCarts.add(cartInfraToCartTransformer.apply(cart));
        }
        return listOfCarts;
    }

    /**
     * @param cart
     * @return
     */
    @Override
    public Cart updateCart(Cart cart) {
        com.giftsncoupons.cart.infrastructure.cart.models.Cart cartInfra = cartRepository
                .save(cartToCartInfraTransformer.apply(cart));
        return cartInfraToCartTransformer.apply(cartInfra);
    }

    /**
     * @param cart
     * @return
     */
    @Override
    public Cart createCart(Cart cart) {
        com.giftsncoupons.cart.infrastructure.cart.models.Cart cartInfra = cartRepository
                .save(cartToCartInfraTransformer.apply(cart));
        return cartInfraToCartTransformer.apply(cartInfra);
    }

    /**
     * @param cartId
     */
    @Override
    public void deleteById(String cartId) {
        cartRepository.deleteById(cartId);
    }

    /**
     * @param cartId
     * @return
     */
    @Override
    public Optional<Cart> findCartById(String cartId) {
        Optional<com.giftsncoupons.cart.infrastructure.cart.models.Cart> cartInfraOptional = cartRepository.findById(cartId);
        if(cartInfraOptional.isPresent()) {
            com.giftsncoupons.cart.infrastructure.cart.models.Cart cartInfra = cartInfraOptional.get();
            Cart cart = cartInfraToCartTransformer.apply(cartInfra);
            return Optional.of(cart);
        }
        return Optional.empty();

    }
}
