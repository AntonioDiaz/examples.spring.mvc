package com.dao;

import java.util.List;

import com.domain.Customer;


public interface CustomerDAO extends GenericDAO<Customer>{

	public List<Customer> findCustomers();
	
}
