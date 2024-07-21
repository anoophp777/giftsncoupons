package com.giftsncoupons.cart.application.services;

import com.giftsncoupons.cart.application.exception.CheckoutException;
import com.giftsncoupons.cart.application.transformer.CartTransformer;
import com.giftsncoupons.cart.application.transformer.OrderRequestTransformer;
import com.giftsncoupons.cart.application.transformer.PromotionTransformer;
import com.giftsncoupons.cart.domain.CheckoutDomainService;
import com.giftsncoupons.cart.domain.models.CartModel;
import com.giftsncoupons.cart.domain.models.PromotionModel;
import com.giftsncoupons.cart.infrastructure.order.OrderRepository;
import com.giftsncoupons.cart.infrastructure.cart.models.Cart;
import com.giftsncoupons.cart.infrastructure.cart.models.Item;
import com.giftsncoupons.cart.infrastructure.logideli.LogiDeliClient;
import com.giftsncoupons.cart.infrastructure.logideli.models.LogiDeliItem;
import com.giftsncoupons.cart.infrastructure.logideli.models.LogiDeliRequest;
import com.giftsncoupons.cart.infrastructure.logideli.models.LogiDeliResponse;
import com.giftsncoupons.cart.infrastructure.order.models.Order;
import com.giftsncoupons.cart.infrastructure.promotion.models.FreeGift;
import com.giftsncoupons.cart.infrastructure.promotion.models.Promotion;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@Slf4j
public class CheckoutService {

    private CartService cartService;
    private OrderRepository orderRepository;
    private PromotionService promotionService;
    private LogiDeliClient logiDeliClient;
    private RedisAtomicInteger redisAtomicInteger;
    private CheckoutDomainService checkoutDomainService;
    private CartTransformer cartTransformer;
    private PromotionTransformer promotionTransformer;
    private PromotionModel promotionModel;
    private OrderRequestTransformer orderRequestTransformer;

    @Autowired
    public CheckoutService(CartService cartService, OrderRepository orderRepository,
                           PromotionService promotionService, LogiDeliClient logiDeliClient,
                           RedisAtomicInteger redisAtomicInteger, CheckoutDomainService checkoutDomainService, CartTransformer cartTransformer, PromotionTransformer promotionTransformer, OrderRequestTransformer orderRequestTransformer) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.promotionService = promotionService;
        this.logiDeliClient = logiDeliClient;
        this.redisAtomicInteger = redisAtomicInteger;
        this.checkoutDomainService = checkoutDomainService;
        this.cartTransformer = cartTransformer;
        this.promotionTransformer = promotionTransformer;
        this.orderRequestTransformer = orderRequestTransformer;
    }

    @PostConstruct
    public void init() {
        Promotion promotion = promotionService.findPromotion("THANKYOU10");
        promotionModel = promotionTransformer.apply(promotion);
    }

    public Order checkout(String cartId) {
        Optional<Cart> cartOptional = cartService.findById(cartId);
        Cart cart;
        if (cartOptional.isPresent()) {
            cart = cartOptional.get();
        } else {
            throw new NoSuchElementException("No value present");
        }

        boolean belowThreshold = redisAtomicInteger.get() < 1000;
        CartModel cartModel = null;
        if (belowThreshold) {
            cartModel = cartTransformer.apply(cart);
            cartModel = checkoutDomainService.processPromotion(cartModel, promotionModel);
        }

        try {
            LogiDeliResponse logiDeliResponse = logiDeliClient
                    .shipCart(checkoutDomainService.convertToLogiDeliRequest(cart));

            if (belowThreshold) {
                int newCount = redisAtomicInteger.decrementAndGet();
                log.info("New count after decrement: {}", newCount);
            }
            Order order = orderRequestTransformer.apply(logiDeliResponse, cartModel);

            order = orderRepository.save(order);

            return order;

        } catch (Exception exception) {
            log.error("Call to LogiDeli wasn't successful", exception);

            throw new CheckoutException(exception.getMessage(), exception);
        }
    }


}
