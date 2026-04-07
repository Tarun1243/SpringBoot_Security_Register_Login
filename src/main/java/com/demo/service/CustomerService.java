package com.demo.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.dao.CustomerDao;
import com.demo.model.Customer;

@Service
public class CustomerService implements UserDetailsService{
	@Autowired
	private CustomerDao dao;
	
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	
	public boolean saveCustomer(Customer c){
		
		
		String encoded = pwdEncoder.encode(c.getPwd()); //encryption completed
		c.setPwd(encoded); //encrypted password is updated and saved into the database
		
		Customer savedCustomer = dao.save(c);
		return savedCustomer.getCid()!=null;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		
		Customer c = dao.findByEmail(email);
		
		
		return new User(c.getEmail(), c.getPwd(), Collections.emptyList());
	}

}