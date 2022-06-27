package com.tejacodes.blogapplication.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tejacodes.blogapplication.dto.SigninDTO;
import com.tejacodes.blogapplication.dto.SignupDTO;
import com.tejacodes.blogapplication.entity.Role;
import com.tejacodes.blogapplication.entity.User;
import com.tejacodes.blogapplication.repository.RoleRepository;
import com.tejacodes.blogapplication.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	/*
	 * The below two methods are not a good practice to follow, as controller is directly interacting with
	 * repository layer, which is not suggested.
	 * Please have a service interface that will register and authenticate the user and then return the result to Controller.
	 */
	@PostMapping("/signup")
	public ResponseEntity<String> registerUser(@RequestBody SignupDTO signupDTO)
	{
		//check for username in DB
		if(userRepository.existsByUsername(signupDTO.getUsername()))
			return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
		
		//check for email in DB
		if(userRepository.existsByEmail(signupDTO.getEmail()))
			return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
		
		User user = new User();
		user.setName(signupDTO.getName());
		user.setUsername(signupDTO.getUsername());
		user.setEmail(signupDTO.getEmail());
		user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
		
		// Assigning the role USER to whoever register to our app
		Role role = roleRepository.findByName("ROLE_USER").get();

		user.setRoles(Collections.singleton(role));
		
		userRepository.save(user);
		
		return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);

	}
	
	@PostMapping("/signin")
	public ResponseEntity<String> authenticateUser(@RequestBody SigninDTO signinDTO)
	{
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signinDTO.getUsernameOrEmail(), signinDTO.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseEntity<>("User signed-in Successfully!", HttpStatus.OK);
	}
}
