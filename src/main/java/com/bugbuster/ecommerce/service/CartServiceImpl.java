package com.bugbuster.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bugbuster.ecommerce.exception.ProductException;
import com.bugbuster.ecommerce.model.Cart;
import com.bugbuster.ecommerce.model.CartItem;
import com.bugbuster.ecommerce.model.Product;
import com.bugbuster.ecommerce.model.User;
import com.bugbuster.ecommerce.repository.CartRepository;
import com.bugbuster.ecommerce.request.AddItemRequest;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private ProductService productSevice;

	@Override
	public Cart createCart(User user) {
		Cart cart = new Cart();
		cart.setUser(user);
		return cartRepository.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
		Cart cart = cartRepository.findByUserId(userId);
		Product product = productSevice.findProductById(req.getProductId());
		CartItem isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
		if (isPresent == null) {
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(req.getQuantity());
			cartItem.setUserId(userId);

			int price = req.getQuantity() * product.getDiscountedPrice();
			cartItem.setPrice(price);
			cartItem.setSize(req.getSize());

			CartItem createdCartItem = cartItemService.createCartItem(cartItem);
			cart.getCartItems().add(createdCartItem);
		}
		return "Item Add To Cart";
	}

	@Override
	public Cart findUserCart(Long userId) {
		Cart cart = cartRepository.findByUserId(userId);

		int totalPrice = 0;
		int totalDiscountedPrice = 0;
		int totalItem = 0;

		for (CartItem cartItem : cart.getCartItems()) {
			totalPrice = totalPrice + cartItem.getPrice();
			totalDiscountedPrice = totalDiscountedPrice + cartItem.getDiscountedPrice();
			totalItem = totalItem + cartItem.getQuantity();
		}

		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		cart.setTotalPrice(totalPrice);
		cart.setDiscount(totalPrice - totalDiscountedPrice);

		return cartRepository.save(cart);
	}

}
