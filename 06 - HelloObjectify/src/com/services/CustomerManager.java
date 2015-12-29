package com.services;

import java.util.List;

import com.domain.Customer;


public interface CustomerManager {

	public List<Customer> customers();
	
	public List<Customer> customers(Customer customer);
	
	public void add(Customer customer) throws Exception;
	
	public boolean remove(Customer customer) throws Exception;
	
	public boolean update(Customer customer) throws Exception;
	
	
}
