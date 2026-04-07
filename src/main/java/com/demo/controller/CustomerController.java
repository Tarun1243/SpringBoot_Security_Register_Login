package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Customer;
import com.demo.service.CustomerService;

@RestController
public class CustomerController 
{
	@Autowired
	private CustomerService service;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Customer c){
		
	/*
	 * System.out.print(c.getEmail()); System.out.print(c.getPwd());
	 * 
	 * 
	 * UsernamePasswordAuthenticationToken token = new
	 * UsernamePasswordAuthenticationToken(c.getEmail(), c.getPwd()); Authentication
	 * authenticate = authManager.authenticate(token); boolean status =
	 * authenticate.isAuthenticated(); if(status) { return new
	 * ResponseEntity<>("login sucess", HttpStatus.OK); }else { return new
	 * ResponseEntity<>("login denied", HttpStatus.BAD_REQUEST); } }
	 */

	        // 🔍 Debug prints
	        System.out.println("Email: " + c.getEmail());
	        System.out.println("Pwd: " + c.getPwd());

	        try {
	            UsernamePasswordAuthenticationToken token =
	                    new UsernamePasswordAuthenticationToken(c.getEmail(), c.getPwd());

	            Authentication auth = authManager.authenticate(token);

	            if (auth.isAuthenticated()) {
	                return new ResponseEntity<>("Login Success", HttpStatus.OK);
	            } else {
	                return new ResponseEntity<>("Login Failed", HttpStatus.UNAUTHORIZED);
	            }

	        } catch (Exception e) {
	            return new ResponseEntity<>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
	        }
	}
	//customer register
	@PostMapping("/register") //http://localhost:7866/register
		
	public ResponseEntity<String> register(@RequestBody Customer c) //ResponseEntity---> returns the status codes
	{
		boolean status = service.saveCustomer(c);
		if(status)
		{
			return new ResponseEntity<>("success",HttpStatus.CREATED);
		}
		else
		{
			return new ResponseEntity<>("not created",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}