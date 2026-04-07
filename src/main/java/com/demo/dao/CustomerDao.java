package com.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.Customer;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {

	public Customer findByEmail(String email);
}
