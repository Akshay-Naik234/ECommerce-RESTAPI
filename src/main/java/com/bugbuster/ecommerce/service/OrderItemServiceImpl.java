package com.bugbuster.ecommerce.service;

import org.springframework.stereotype.Service;

import com.bugbuster.ecommerce.model.OrderItem;
import com.bugbuster.ecommerce.repository.OrderItemRepository;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	private OrderItemRepository orderItemRepository;
	
	public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
		this.orderItemRepository=orderItemRepository;
	}
	
	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		
		return orderItemRepository.save(orderItem);
	}

}
