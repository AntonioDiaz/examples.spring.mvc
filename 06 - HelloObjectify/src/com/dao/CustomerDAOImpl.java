package com.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.domain.Customer;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Override
	public void create(Customer customer) throws Exception {
		ofy().save().entity(customer).now();
	}

	@Override
	public boolean update(Customer customer) throws Exception {
		boolean updateResult;
		if (customer == null || customer.getId() == null) {
			updateResult = false;
		} else {
			Customer c = ofy().load().type(Customer.class).id(customer.getId()).now();
			if (c != null) {
				ofy().save().entity(customer).now();
				updateResult = true;
			} else {
				updateResult = false;
			}
		}
		return updateResult;
	}

	@Override
	public boolean remove(Customer customer) throws Exception {
		ofy().delete().entity(customer).now();
		return true;
	}

	@Override
	public List<Customer> findAllCustomers() {
		Query<Customer> query = ofy().load().type(Customer.class);
		return query.order("name").list();		
	}

	@Override
	public List<Customer> findCustomers(Customer customer) {
		Query<Customer> query = ofy().load().type(Customer.class);
		List<Customer> returnList = new ArrayList<Customer>();
		if (customer.getId()!=null) {
			Customer c = ofy().load().key(Key.create(Customer.class, customer.getId())).now();
			if (c!=null) {
				returnList.add(c);
			}
		} else {
			if (StringUtils.isNotBlank(customer.getName())) {
				query = query.filter("name", customer.getName());
			}
			if (StringUtils.isNotBlank(customer.getSurname01())) {
				query = query.filter("surname01", customer.getSurname01());
			}
			if (StringUtils.isNotBlank(customer.getSurname02())) {
				query = query.filter("surname02", customer.getSurname02());
			}
			returnList = query.order("name").list();
		}
		return returnList;
	}

}
