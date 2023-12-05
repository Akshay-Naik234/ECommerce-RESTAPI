package com.bugbuster.ecommerce.service;

import java.util.List;

import com.bugbuster.ecommerce.exception.ProductException;
import com.bugbuster.ecommerce.model.Rating;
import com.bugbuster.ecommerce.model.User;
import com.bugbuster.ecommerce.request.RatingRequest;

public interface RatingService {
	
	public Rating createRating(RatingRequest req,User user) throws ProductException;
;
	public List<Rating> getProductsRating(Long productId);
}
