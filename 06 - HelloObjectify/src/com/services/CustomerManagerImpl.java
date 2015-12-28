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
		List<Customer> customers = dao.findCustomers();
		return customers;
	}
}
