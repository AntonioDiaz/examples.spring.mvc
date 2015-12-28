package com.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.domain.Customer;


@Service
public interface CustomerManager {

	public List<Customer> customers();
	
}
