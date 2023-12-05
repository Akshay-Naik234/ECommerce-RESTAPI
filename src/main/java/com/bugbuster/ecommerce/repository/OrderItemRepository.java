package com.bugbuster.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugbuster.ecommerce.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
