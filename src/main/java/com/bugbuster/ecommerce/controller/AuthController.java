package com.bugbuster.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bugbuster.ecommerce.config.JwtProvider;
import com.bugbuster.ecommerce.exception.UserException;
import com.bugbuster.ecommerce.model.Cart;
import com.bugbuster.ecommerce.model.User;
import com.bugbuster.ecommerce.repository.UserRepository;
import com.bugbuster.ecommerce.request.LoginRequest;
import com.bugbuster.ecommerce.response.AuthResponse;
import com.bugbuster.ecommerce.service.CartService;
import com.bugbuster.ecommerce.service.CustomUserServiceImplementation;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CartService cartService;

	@Autowired
	private CustomUserServiceImplementation customUserService;
	
	@Autowired
	private JwtProvider jwtProvider;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
		String email = user.getEmail();
		String password = user.getPassword();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();

		User isEmailExist = userRepository.findByEmail(email);
		if (isEmailExist != null) {
			throw new UserException("Email is Already Used with Another Account");
		}

		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setFirstName(firstName);
		createdUser.setLastName(lastName);

		User savedUser = userRepository.save(createdUser);
		Cart cart = cartService.createCart(savedUser);
		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),
				savedUser.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("Signup Success");

		return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest){
		String username = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		
		Authentication authentication = authenticate(username,password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.generateToken(authentication);

		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("Signin Success");
		System.out.println(authResponse.toString());
		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails = customUserService.loadUserByUsername(username);
		if(userDetails == null) {
			throw new BadCredentialsException("Invalid Username ...");
		}
		
		if(!passwordEncoder.matches(password, userDetails.getPassword()))
		{	
			throw new BadCredentialsException("Invalid Password ...");
		}
		
		
		return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null,userDetails.getAuthorities());
	}
}
