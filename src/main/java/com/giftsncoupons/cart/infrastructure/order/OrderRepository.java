package com.giftsncoupons.cart.infrastructure.order;

import com.giftsncoupons.cart.infrastructure.order.models.Order;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CassandraRepository<Order, String> {
}
