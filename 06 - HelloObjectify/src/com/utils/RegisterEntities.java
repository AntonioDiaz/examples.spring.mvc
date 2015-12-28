package com.utils;

import org.springframework.beans.factory.annotation.Autowired;

import com.dao.CustomerDAO;
import com.domain.Customer;
import com.googlecode.objectify.ObjectifyService;



public class RegisterEntities {

	//private static final Logger logger = LoggerFactory.getLogger(CustomerDAOImpl.class);

	@Autowired
	CustomerDAO customerDAO;
	
	public void init() {
		ObjectifyService.register(Customer.class);
		if (customerDAO.findCustomers().size()==0) {
			Customer customer = new Customer();
			customer.setName("zasca");
			try {
				customerDAO.create(customer);
			} catch (Exception e) {
				
			}
		}
		
		
	}
	
}
