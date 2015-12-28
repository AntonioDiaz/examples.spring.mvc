package com.dao;

import java.util.List;


import org.springframework.stereotype.Repository;

import com.domain.Customer;


import static com.googlecode.objectify.ObjectifyService.ofy;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Override
	public void create(Customer customer) throws Exception {
		ofy().save().entity(customer).now();

	}

	@Override
	public boolean update(Customer item) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Customer item) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Customer> findCustomers() {
		List<Customer> customers = ofy().load().type(Customer.class).list();
		return customers;		
	}

}
