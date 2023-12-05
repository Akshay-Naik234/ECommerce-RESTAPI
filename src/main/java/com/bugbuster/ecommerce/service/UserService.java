package com.bugbuster.ecommerce.service;

import com.bugbuster.ecommerce.exception.UserException;
import com.bugbuster.ecommerce.model.User;

public interface UserService {
	public User findUserById(Long userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
}
