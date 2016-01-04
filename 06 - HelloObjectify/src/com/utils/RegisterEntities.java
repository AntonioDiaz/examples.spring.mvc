package com.utils;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.domain.Customer;
import com.domain.Order;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.services.CustomerManager;
import com.services.OrderManager;


public class RegisterEntities {

	private static final Logger logger = Logger.getLogger(RegisterEntities.class);
	private static final List<String> names = Arrays.asList(new String[]{"Antonio", "Maria", "Laura", "David" ,"Angel", "Lucas", "Pedro", "Miguel", "Carlos", "Lorena"});
	private static final List<String> surnames = Arrays.asList(new String[]{"Diaz", "Arroyo", "Isasi", "Lopez" ,"Gonzalez", "Garcia", "Jimenez", "Gistau", "Escobar"});
	
	@Autowired
	CustomerManager customerManager;
	
	@Autowired
	OrderManager orderManager;
	
	public void init() {
		logger.info("registrando Entities");
		ObjectifyService.register(Customer.class);
		ObjectifyService.register(Order.class);
		/* delete all customers. */
		try {
			List<Customer> findCustomers = customerManager.customers();
			for (Customer customer : findCustomers) {
				customerManager.remove(customer);
			}
			if (customerManager.customers().size()==0) {
				for(int i=0; i<35; i++) {
					int nameIndex = ThreadLocalRandom.current().nextInt(0, names.size());
					int surname01Index = ThreadLocalRandom.current().nextInt(0, surnames.size());
					int surname02Index = ThreadLocalRandom.current().nextInt(0, surnames.size());
					/* create the order. */
					Order order = new Order();
					order.setName("Order by "+ names.get(nameIndex) + ", " + surnames.get(surname01Index) + ", " + surnames.get(surname02Index));
					order.setCreateDate(new Date());
					orderManager.add(order);
					Ref<Order> orderKey = Ref.create(order);
					/* create the customer. */
					Customer customer = new Customer();
					customer.setName(names.get(nameIndex));
					customer.setSurname01(surnames.get(surname01Index));
					customer.setSurname02(surnames.get(surname02Index));					
					customer.getOrders().add(orderKey);
					customerManager.add(customer);
				}
			}
			
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
