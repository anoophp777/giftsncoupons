package com.giftsncoupons.cart.infrastructure;

import com.giftsncoupons.cart.infrastructure.cart.CartRepository;
import com.giftsncoupons.cart.infrastructure.cart.models.Cart;
import com.giftsncoupons.cart.infrastructure.promotion.models.FreeGift;
import com.giftsncoupons.cart.infrastructure.cart.models.Item;
import com.giftsncoupons.cart.infrastructure.promotion.models.Promotion;
import com.giftsncoupons.cart.infrastructure.promotion.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class LoadData {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    private RedisAtomicInteger redisAtomicInteger;

    @Autowired
    PromotionRepository promotionRepository;

    @Bean
    ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {

                Item pressureCooker = Item.builder().itemId("1").name("PressureCooker").price(1500).build();
                Item blender = Item.builder().itemId("1").name("Blender").price(1000).build();
                Item coffeePot = Item.builder().itemId("1").name("CoffeePot").price(500).build();

                Cart cart1 = Cart.builder().userId("1234").items(new ArrayList<>(List.of(pressureCooker))).totalPrice(1999).build();
                Cart cart2 = Cart.builder().userId("2345").items(new ArrayList<>(List.of(pressureCooker))).totalPrice(1000).build();
                Cart cart3 = Cart.builder().userId("3456").items(new ArrayList<>(List.of(pressureCooker))).totalPrice(2000).build();
                cartRepository.saveAll(List.of(cart1, cart2, cart3));

                LocalDate date = LocalDate.now();

                FreeGift freeGift = FreeGift.builder().giftId("1").quantity(1)
                        .startDate(date.minusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())
                        .endDate(date.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())
                        .build();
                Promotion thankyou10 = Promotion.builder().couponCode("THANKYOU10").freeGift(List.of(freeGift))
                        .startDate(date.minusDays(5).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())
                        .endDate(date.plusDays(5).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())
                        .build();
                promotionRepository.save(thankyou10);

                redisAtomicInteger.set(5);
            }
        };
    }
}
