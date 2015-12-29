package com.services;

import org.springframework.stereotype.Service;

import com.domain.Order;


@Service
public interface OrderManager {

	public void add(Order order) throws Exception;
	
}
