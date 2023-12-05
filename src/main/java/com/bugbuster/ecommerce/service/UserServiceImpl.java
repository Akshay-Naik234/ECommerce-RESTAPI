package com.bugbuster.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bugbuster.ecommerce.config.JwtProvider;
import com.bugbuster.ecommerce.exception.UserException;
import com.bugbuster.ecommerce.model.User;
import com.bugbuster.ecommerce.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtProvider jwtProvider;

	@Override
	public User findUserById(Long userId) throws UserException {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			return user.get();
		}

		throw new UserException("user not found with id : " + userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email = jwtProvider.getEmailFromToken(jwt);
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UserException("user not found with email " + email);
		}
		return user;
	}

}
