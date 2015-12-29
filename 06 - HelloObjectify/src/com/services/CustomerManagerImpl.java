package com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CustomerDAO;
import com.domain.Customer;


@Service ("customerManager")
public class CustomerManagerImpl implements CustomerManager {

	 @Autowired CustomerDAO dao;
	
	@Override
	public List<Customer> customers() {		
		return dao.findAllCustomers();
	}

	@Override
	public List<Customer> customers(Customer customer) {
		return dao.findCustomers(customer);
	}


	@Override
	public void add(Customer customer) throws Exception{
		dao.create(customer);		
	}
	
	@Override
	public boolean remove(Customer customer) throws Exception {
		return dao.remove(customer);
	}

	@Override
	public boolean update(Customer customer) throws Exception {
		return dao.update(customer);
	}
	
}
