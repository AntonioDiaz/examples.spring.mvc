package com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.OrderDAO;
import com.domain.Order;

@Service ("orderManager")
public class OrderManagerImpl implements OrderManager {

	@Autowired
	OrderDAO orderDAO;
	
	@Override
	public void add(Order order) throws Exception {		
		orderDAO.create(order);
	}

}
