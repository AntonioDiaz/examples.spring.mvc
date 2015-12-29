package com.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import org.springframework.stereotype.Repository;

import com.domain.Order;

@Repository
public class OrderDAOImpl implements OrderDAO{

	@Override
	public void create(Order order) throws Exception {
		ofy().save().entity(order).now();
	}

	@Override
	public boolean update(Order order) throws Exception {
		boolean updateResult;
		if (order == null || order.getId() == null) {
			updateResult = false;
		} else {
			Order c = ofy().load().type(Order.class).id(order.getId()).now();
			if (c != null) {
				ofy().save().entity(order).now();
				updateResult = true;
			} else {
				updateResult = false;
			}
		}
		return updateResult;
	}

	@Override
	public boolean remove(Order order) throws Exception {
		ofy().delete().entity(order).now();
		return true;
	}
}
