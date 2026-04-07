package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.demo.service.CustomerService;

@Configuration //this class will acts as a configuraton
@EnableWebSecurity //this is used to enable security configuration
public class AppSecurityConfig 
{
	//wiring the service layer object
	@Autowired
	private CustomerService service;
	
	//password encryption
	@Bean
	public BCryptPasswordEncoder pwdEncoder()
	{
		return new BCryptPasswordEncoder(); 
	}

	//to load the details of customer from service and encrypted data
	@Bean
	public DaoAuthenticationProvider authProvider()
	{
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(service);
		
		authProvider.setPasswordEncoder(pwdEncoder());
		return authProvider;
	}
	
	//create authenticationmanager
	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration config)
	{
		return config.getAuthenticationManager();
	}
	
	//filtering the requests
	@Bean
	public SecurityFilterChain security(HttpSecurity http)
	{
		http.authorizeHttpRequests((req) ->
		{
			req.requestMatchers("/register","/login")
				.permitAll()
				.anyRequest()
				.authenticated();
		});
		
		return http.csrf(csrf->csrf.disable()).build();
	}
	
	
}