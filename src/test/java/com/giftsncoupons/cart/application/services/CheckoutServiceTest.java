package com.giftsncoupons.cart.application.services;

import com.giftsncoupons.cart.application.services.cart.CartServiceImpl;
import com.giftsncoupons.cart.application.services.checkout.CheckoutServiceImpl;
import com.giftsncoupons.cart.application.services.promotion.PromotionServiceImpl;
import com.giftsncoupons.cart.application.services.transformer.*;
import com.giftsncoupons.cart.controller.models.Cart;
import com.giftsncoupons.cart.controller.models.CheckoutResponse;
import com.giftsncoupons.cart.domain.CheckoutDomainService;
import com.giftsncoupons.cart.domain.models.PromotionModel;
import com.giftsncoupons.cart.infrastructure.logideli.LogiDeliClient;
import com.giftsncoupons.cart.infrastructure.logideli.models.LogiDeliResponse;
import com.giftsncoupons.cart.infrastructure.order.OrderRepository;
import com.giftsncoupons.cart.infrastructure.order.models.Order;
import com.giftsncoupons.cart.infrastructure.redis.RedisClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceTest {

    CheckoutServiceImpl checkoutService;

    @Mock
    private CartServiceImpl cartService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private PromotionServiceImpl promotionService;
    @Mock
    private LogiDeliClient logiDeliClient;
    @Mock
    private CheckoutDomainService checkoutDomainService;
    @Mock
    private CartTransformer cartTransformer;
    @Mock
    private PromotionTransformer promotionTransformer;
    @Mock
    private PromotionModel promotionModel;
    @Mock
    private OrderRequestTransformer orderRequestTransformer;
    @Mock
    private RedisClient redisClient;
    @Mock
    private OrderToCheckoutTransformer orderToCheckoutTransformer;
    @Mock
    private CartModelToCartTransformer cartModelToCartTransformer;

    @BeforeEach
    void setUp() {

        checkoutService = new CheckoutServiceImpl(cartService, orderRepository, promotionService,
                logiDeliClient, checkoutDomainService, cartTransformer,
                promotionTransformer, orderRequestTransformer, redisClient,
                orderToCheckoutTransformer, cartModelToCartTransformer, true);

        com.giftsncoupons.cart.controller.models.Item pressureCooker = com.giftsncoupons.cart.controller.models.Item.builder().itemId("1").name("PressureCooker").price(1500).build();
        com.giftsncoupons.cart.controller.models.Item coffeePot = com.giftsncoupons.cart.controller.models.Item.builder().itemId("3").name("CoffeePot").price(1500).build();
        com.giftsncoupons.cart.controller.models.Cart cart = com.giftsncoupons.cart.controller.models.Cart.builder()
                .userId("1234")
                .items(new ArrayList<>(List.of(pressureCooker)))
                .totalPrice(1999)
                .build();
        lenient().when(cartService.findCartById(any(String.class))).thenReturn(Optional.of(cart));
        LocalDate date = LocalDate.now();
        com.giftsncoupons.cart.controller.models.FreeGift freeGift = com.giftsncoupons.cart.controller.models.FreeGift.builder().giftId("3").quantity(1)
                .name("CoffeePot")
                .startDate(date.minusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())
                .endDate(date.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())
                .build();
        com.giftsncoupons.cart.controller.models.Promotion thankyou7 = com.giftsncoupons.cart.controller.models.Promotion.builder().couponCode("THANKYOU7").freeGifts(List.of(freeGift))
                .startDate(date.minusDays(5).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())
                .endDate(date.plusDays(5).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())
                .build();
        lenient().when(promotionService.findPromotion("THANKYOU7")).thenReturn(thankyou7);
        LogiDeliResponse logiDeliResponse = LogiDeliResponse.builder()
                .shippingCost(10)
                .deliveryDate(date.plusDays(2)
                        .atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())
                .build();
        lenient().when(logiDeliClient.shipCart(any())).thenReturn(logiDeliResponse);
        lenient().when(cartTransformer.apply(any(com.giftsncoupons.cart.controller.models.Cart.class))).thenCallRealMethod();
        lenient().when(checkoutDomainService.addFreeGiftPromotion(any(), any())).thenCallRealMethod();
        lenient().when(promotionTransformer.apply(any())).thenCallRealMethod();
        lenient().when(orderRequestTransformer.apply(any(), any())).thenCallRealMethod();
        lenient().when(orderRepository.save(any())).thenReturn(Mockito.mock(Order.class));
        lenient().when(orderToCheckoutTransformer.apply(any(), any())).thenReturn(CheckoutResponse.builder()
                .confirmationId("888")
                .cart(Cart.builder().items(List.of(pressureCooker, coffeePot)).build())
                .userId("789").shippingCost(10.0)
                .deliveryDate(1721656947).build());
    }

    @Test
    void checkoutNoCart() {
        when(cartService.findCartById(any(String.class))).thenReturn(Optional.ofNullable(null));

        assertThrows(NoSuchElementException.class, () -> checkoutService.checkout("0000"));
    }

    @Test
    void checkoutSuccessWithPromotion() {
        when(redisClient.getAtomicInteger()).thenReturn(5);

        CheckoutResponse checkout = checkoutService.checkout("1234");
        assertEquals(2, checkout.getCart().getItems().size());
        assertEquals(checkout.getConfirmationId(), "888");
        assertEquals(checkout.getShippingCost(), 10.0);
        assertEquals(checkout.getUserId(), "789");
        assertEquals(checkout.getDeliveryDate(), 1721656947);
    }

    @Test
    void checkoutSuccessWithoutPromotion() {
        CheckoutServiceImpl checkoutServiceWithoutPromo = new CheckoutServiceImpl(cartService, orderRepository, promotionService,
                logiDeliClient, checkoutDomainService, cartTransformer,
                promotionTransformer, orderRequestTransformer, redisClient,
                orderToCheckoutTransformer, cartModelToCartTransformer, false);
        com.giftsncoupons.cart.controller.models.Item pressureCooker = com.giftsncoupons.cart.controller.models.Item.builder().itemId("1").name("PressureCooker").price(1500).build();

        lenient().when(orderToCheckoutTransformer.apply(any(), any())).thenReturn(CheckoutResponse.builder()
                .confirmationId("888")
                .cart(Cart.builder().items(List.of(pressureCooker)).build())
                .userId("789").shippingCost(10.0)
                .deliveryDate(1721656947).build());
        lenient().when(redisClient.getAtomicInteger()).thenReturn(5);

        CheckoutResponse checkout = checkoutServiceWithoutPromo.checkout("1234");
        assertEquals(1, checkout.getCart().getItems().size());
        assertEquals(checkout.getConfirmationId(), "888");
        assertEquals(checkout.getShippingCost(), 10.0);
        assertEquals(checkout.getUserId(), "789");
        assertEquals(checkout.getDeliveryDate(), 1721656947);
    }


}