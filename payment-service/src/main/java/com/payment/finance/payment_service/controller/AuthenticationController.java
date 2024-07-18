package com.payment.finance.payment_service.controller;

import com.payment.finance.payment_service.entity.*;
import com.payment.finance.payment_service.exception.*;
import com.payment.finance.payment_service.response.*;
import com.payment.finance.payment_service.security.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController

public class AuthenticationController {
	
	private final AuthenticationService authService;

	public AuthenticationController(AuthenticationService authService) {
		super();
		this.authService = authService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody Users user) throws UserEmailIdExistsException
	{
		 return ResponseEntity.ok(authService.register(user));
	}
	
	@PostMapping("/adminRegister")
	public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody Users user) throws UserEmailIdExistsException
	{    
		 user.setRole(Role.ADMIN);
		 return ResponseEntity.ok(authService.register(user));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse>  login(@RequestBody Users request) throws Exception{
		
		return ResponseEntity.ok(authService.authenticate(request));
	}
	
	@GetMapping("/test")
	public ResponseEntity<String> testLogin()
	{
		System.out.println("Success accessed");
		return ResponseEntity.ok("Secured url");
	}
	
	
}
