package com.giftsncoupons.cart.infrastructure.promotion;

import com.giftsncoupons.cart.infrastructure.promotion.models.Promotion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends CrudRepository<Promotion, String> {
}
