package com.giftsncoupons.cart.application.services;

import com.giftsncoupons.cart.application.exception.CheckoutException;
import com.giftsncoupons.cart.application.transformer.CartTransformer;
import com.giftsncoupons.cart.application.transformer.OrderRequestTransformer;
import com.giftsncoupons.cart.application.transformer.OrderToCheckoutTransformer;
import com.giftsncoupons.cart.application.transformer.PromotionTransformer;
import com.giftsncoupons.cart.controller.checkout.models.CheckoutResponse;
import com.giftsncoupons.cart.domain.CheckoutDomainService;
import com.giftsncoupons.cart.domain.models.CartModel;
import com.giftsncoupons.cart.domain.models.PromotionModel;
import com.giftsncoupons.cart.infrastructure.cart.models.Cart;
import com.giftsncoupons.cart.infrastructure.logideli.LogiDeliClient;
import com.giftsncoupons.cart.infrastructure.logideli.models.LogiDeliResponse;
import com.giftsncoupons.cart.infrastructure.order.OrderRepository;
import com.giftsncoupons.cart.infrastructure.order.models.Order;
import com.giftsncoupons.cart.infrastructure.promotion.models.Promotion;
import com.giftsncoupons.cart.infrastructure.redis.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class CheckoutService {

    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final PromotionService promotionService;
    private final LogiDeliClient logiDeliClient;
    private final CheckoutDomainService checkoutDomainService;
    private final CartTransformer cartTransformer;
    private final PromotionTransformer promotionTransformer;
    private final OrderRequestTransformer orderRequestTransformer;
    private final RedisClient redisClient;
    private final OrderToCheckoutTransformer orderToCheckoutTransformer;
    private final boolean freeGiftPromoEnabled;

    @Autowired
    public CheckoutService(CartService cartService, OrderRepository orderRepository,
                           PromotionService promotionService, LogiDeliClient logiDeliClient,
                           CheckoutDomainService checkoutDomainService, CartTransformer cartTransformer,
                           PromotionTransformer promotionTransformer, OrderRequestTransformer orderRequestTransformer,
                           RedisClient redisClient, OrderToCheckoutTransformer orderToCheckoutTransformer,
                           @Value("${giftsncoupons.promotion.freegift.enable:false}") boolean freeGiftPromoEnabled) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.promotionService = promotionService;
        this.logiDeliClient = logiDeliClient;
        this.checkoutDomainService = checkoutDomainService;
        this.cartTransformer = cartTransformer;
        this.promotionTransformer = promotionTransformer;
        this.orderRequestTransformer = orderRequestTransformer;
        this.redisClient = redisClient;
        this.orderToCheckoutTransformer = orderToCheckoutTransformer;
        this.freeGiftPromoEnabled = freeGiftPromoEnabled;
    }

    public CheckoutResponse checkout(String cartId) {
        Optional<Cart> cartOptional = cartService.findById(cartId);
        Cart cart;
        if (cartOptional.isPresent()) {
            cart = cartOptional.get();
        } else {
            throw new NoSuchElementException("No value present");
        }

        CartModel cartModel = null;
        cartModel = cartTransformer.apply(cart);

        if (freeGiftPromoEnabled) {
            int atomicInteger = redisClient.getAtomicInteger();
            if (atomicInteger >= 0 && atomicInteger < 1000) {
                return processFreeGiftPromotion(cartModel);
            }
        }
        CheckoutResponse checkoutResponse = callLogiDeli(cartModel);

        return checkoutResponse;
    }

    private CheckoutResponse processLogiDeliResponse(LogiDeliResponse logiDeliResponse, CartModel cartModel) {
        Order order = orderRequestTransformer.apply(logiDeliResponse, cartModel);

        order = orderRepository.save(order);

        return orderToCheckoutTransformer.apply(order);
    }

    private CheckoutResponse processFreeGiftPromotion(CartModel cartModel) {

        Promotion promotion = promotionService.findPromotion("THANKYOU10");
        PromotionModel promotionModel = promotionTransformer.apply(promotion);

        cartModel = checkoutDomainService.addFreeGiftPromotion(cartModel, promotionModel);

        CheckoutResponse checkoutResponse = callLogiDeli(cartModel);

        int newCount = redisClient.decrementAndGet();
        log.info("New count after decrement: {}", newCount);

        return checkoutResponse;
    }

    private CheckoutResponse callLogiDeli(CartModel cart) {
        try {
            LogiDeliResponse logiDeliResponse = logiDeliClient
                    .shipCart(checkoutDomainService.convertToLogiDeliRequest(cart));

            return processLogiDeliResponse(logiDeliResponse, cart);

        } catch (Exception exception) {
            log.error("Call to LogiDeli wasn't successful", exception);

            throw new CheckoutException(exception.getMessage(), exception);
        }
    }

}
