package com.bugbuster.ecommerce.service;

import java.util.List;

import com.bugbuster.ecommerce.exception.ProductException;
import com.bugbuster.ecommerce.model.Review;
import com.bugbuster.ecommerce.model.User;
import com.bugbuster.ecommerce.request.ReviewRequest;

public interface ReviewService {

	public Review createReview(ReviewRequest req,User user) throws ProductException;

	public List<Review> getAllReview(Long productId);
}
