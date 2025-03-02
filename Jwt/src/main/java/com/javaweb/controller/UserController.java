package com.javaweb.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import com.javaweb.entity.User;
import com.javaweb.services.JwtService;
import com.javaweb.services.UserService;

@RequestMapping("/users")
@RestController
public class UserController {
	private final UserService userService;
	private final JwtService jwtService;
	
	public UserController(UserService userService,JwtService jwtService) {
		this.userService = userService;
		this.jwtService =  jwtService;
	}
	
	@GetMapping("/me")
	public ResponseEntity<User> authenticatedUser(HttpServletRequest request){
		String email = "";
		try {

	        String authHeader = request.getHeader("Authorization");


	        if (authHeader != null && authHeader.startsWith("Bearer ")) {
	            String jwtToken = authHeader.substring(7); 

	            email = jwtService.extractUsername(jwtToken);

	    } }catch (Exception e) {
	        e.printStackTrace();

	    }
		User user = userService.findByEmail(email).get();
        return ResponseEntity.ok(user);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<User>> allUsers(){
		List<User> users = userService.allUsers();
		return ResponseEntity.ok(users);
	}

}
