package com.springboot.bankDemo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.bankDemo.model.User;
import com.springboot.bankDemo.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public User signUp(User user) {
		String plainPassoword = user.getPassword();							// plain password
		String encodedPassword = passwordEncoder.encode(plainPassoword);	// encodes the plain password
		user.setPassword(encodedPassword);									// encrypted password set to user password 
		return userRepository.save(user);
	}
}
