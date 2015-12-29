package com.dao;

import java.util.List;

import com.domain.Customer;


public interface CustomerDAO extends GenericDAO<Customer>{

	public List<Customer> findAllCustomers();
	
	public List<Customer> findCustomers(Customer customer);
	
}
