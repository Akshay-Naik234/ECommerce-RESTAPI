package com.bugbuster.ecommerce.service;

import com.bugbuster.ecommerce.exception.ProductException;
import com.bugbuster.ecommerce.model.Cart;
import com.bugbuster.ecommerce.model.User;
import com.bugbuster.ecommerce.request.AddItemRequest;

public interface CartService {
	public Cart createCart(User user);

	public String addCartItem(Long userId, AddItemRequest req) throws ProductException;

	public Cart findUserCart(Long userId);
}
