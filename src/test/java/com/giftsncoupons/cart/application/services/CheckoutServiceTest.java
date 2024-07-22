package com.giftsncoupons.cart.application.services;

import com.giftsncoupons.cart.application.transformer.CartTransformer;
import com.giftsncoupons.cart.application.transformer.OrderRequestTransformer;
import com.giftsncoupons.cart.application.transformer.OrderToCheckoutTransformer;
import com.giftsncoupons.cart.application.transformer.PromotionTransformer;
import com.giftsncoupons.cart.controller.checkout.models.CheckoutResponse;
import com.giftsncoupons.cart.domain.CheckoutDomainService;
import com.giftsncoupons.cart.domain.models.PromotionModel;
import com.giftsncoupons.cart.infrastructure.cart.models.Cart;
import com.giftsncoupons.cart.infrastructure.cart.models.Item;
import com.giftsncoupons.cart.infrastructure.logideli.LogiDeliClient;
import com.giftsncoupons.cart.infrastructure.logideli.models.LogiDeliResponse;
import com.giftsncoupons.cart.infrastructure.order.OrderRepository;
import com.giftsncoupons.cart.infrastructure.order.models.Order;
import com.giftsncoupons.cart.infrastructure.promotion.models.FreeGift;
import com.giftsncoupons.cart.infrastructure.promotion.models.Promotion;
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

    CheckoutService checkoutService;

    @Mock
    private CartService cartService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private PromotionService promotionService;
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

    @BeforeEach
    void setUp() {

        checkoutService = new CheckoutService(cartService, orderRepository, promotionService,
                logiDeliClient, checkoutDomainService, cartTransformer,
                promotionTransformer, orderRequestTransformer, redisClient,
                orderToCheckoutTransformer, true);

        Item pressureCooker = Item.builder().itemId("1").name("PressureCooker").price(1500).build();
        Cart cart = Cart.builder()
                .userId("1234")
                .items(new ArrayList<>(List.of(pressureCooker)))
                .totalPrice(1999)
                .build();
        lenient().when(cartService.findById(any(String.class))).thenReturn(Optional.of(cart));
        LocalDate date = LocalDate.now();
        FreeGift freeGift = FreeGift.builder().giftId("1").quantity(1)
                .startDate(date.minusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())
                .endDate(date.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())
                .build();
        Promotion thankyou10 = Promotion.builder().couponCode("THANKYOU10").freeGift(List.of(freeGift))
                .startDate(date.minusDays(5).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())
                .endDate(date.plusDays(5).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())
                .build();
        lenient().when(promotionService.findPromotion("THANKYOU10")).thenReturn(thankyou10);
        LogiDeliResponse logiDeliResponse = LogiDeliResponse.builder()
                .shippingCost(10)
                .deliveryDate(date.plusDays(2)
                        .atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())
                .build();
        lenient().when(logiDeliClient.shipCart(any())).thenReturn(logiDeliResponse);
        lenient().when(cartTransformer.apply(any(Cart.class))).thenCallRealMethod();
        lenient().when(checkoutDomainService.addFreeGiftPromotion(any(), any())).thenCallRealMethod();
        lenient().when(promotionTransformer.apply(any())).thenCallRealMethod();
        lenient().when(orderRequestTransformer.apply(any(), any())).thenCallRealMethod();
        lenient().when(orderRepository.save(any())).thenReturn(Mockito.mock(Order.class));
        lenient().when(orderToCheckoutTransformer.apply(any())).thenReturn(CheckoutResponse.builder().confirmationId("888")
                .userId("789").shippingCost(10.0).deliveryDate(1721656947).build());
    }

    @Test
    void checkoutNoCart() {
        when(cartService.findById(any(String.class))).thenReturn(Optional.ofNullable(null));

        assertThrows(NoSuchElementException.class, () -> checkoutService.checkout("0000"));
    }

    @Test
    void checkoutSuccessWithPromotion() {
        when(redisClient.getAtomicInteger()).thenReturn(5);

        CheckoutResponse checkout = checkoutService.checkout("1234");
        assertEquals(checkout.getConfirmationId(), "888");
        assertEquals(checkout.getShippingCost(), 10.0);
        assertEquals(checkout.getUserId(), "789");
        assertEquals(checkout.getDeliveryDate(), 1721656947);
    }

    @Test
    void checkoutSuccessWithoutPromotion() {
        CheckoutService checkoutServiceWithoutPromo = new CheckoutService(cartService, orderRepository, promotionService,
                logiDeliClient, checkoutDomainService, cartTransformer,
                promotionTransformer, orderRequestTransformer, redisClient,
                orderToCheckoutTransformer, false);
        lenient().when(redisClient.getAtomicInteger()).thenReturn(5);

        CheckoutResponse checkout = checkoutServiceWithoutPromo.checkout("1234");
        assertEquals(checkout.getConfirmationId(), "888");
        assertEquals(checkout.getShippingCost(), 10.0);
        assertEquals(checkout.getUserId(), "789");
        assertEquals(checkout.getDeliveryDate(), 1721656947);
    }


}