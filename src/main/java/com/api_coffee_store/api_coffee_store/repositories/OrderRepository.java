package com.api_coffee_store.api_coffee_store.repositories;

import com.api_coffee_store.api_coffee_store.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByUserId(String userId);
}
