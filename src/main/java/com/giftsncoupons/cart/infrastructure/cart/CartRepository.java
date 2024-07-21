package com.giftsncoupons.cart.infrastructure.cart;

import com.giftsncoupons.cart.infrastructure.cart.models.Cart;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CassandraRepository<Cart, String> {
}

