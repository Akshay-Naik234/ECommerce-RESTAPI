package com.bugbuster.ecommerce.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.bugbuster.ecommerce.exception.ProductException;
import com.bugbuster.ecommerce.model.Product;
import com.bugbuster.ecommerce.request.CreateProductRequest;

public interface ProductService {
	
	//Only Admin
	public Product createProduct(CreateProductRequest req);

	public String deleteProduct(Long productId) throws ProductException;

	public Product updateProduct(Long productId, Product req) throws ProductException;

	//For user and admin both
	public Product findProductById(Long id) throws ProductException;

	public List<Product> findProductByCategory(String category);
	
	public List<Product> getAllProducts();
	
	public List<Product> searchProduct(String query);
	
	public Page<Product> getAllProduct(String category,List<String> colors,List<String> sizes,Integer minPrice,
			Integer maxPrice,Integer minDiscount,String sort,String stock,
			Integer pageNumber,Integer pageSize);
}
