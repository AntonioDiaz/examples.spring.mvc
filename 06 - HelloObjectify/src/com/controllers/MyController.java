package com.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.Customer;
import com.domain.Order;
import com.googlecode.objectify.Ref;
import com.services.CustomerManager;

@Controller
public class MyController {

	private static final Logger logger = Logger.getLogger(MyController.class);
	
	@Autowired
	CustomerManager customerManager;

	@RequestMapping(value = {"/customersList","/"}, method = RequestMethod.GET)
	public String customerList(ModelMap modelMap) {
		modelMap.addAttribute("customersList", customerManager.customers());
		return "customers";
	}
	
	
	@RequestMapping(value = "/doSearch")
	public @ResponseBody List<Map<String, Object>> doSearch(
			@RequestParam(value = "id", required = false, defaultValue = "") Long id,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "surname01", required = false, defaultValue = "") String surname01,
			@RequestParam(value = "surname02", required = false, defaultValue = "") String surname02) {
		logger.debug("searching customers");
		Customer customer = new Customer();
		customer.setId(id);
		customer.setName(name);
		customer.setSurname01(surname01);
		customer.setSurname02(surname02);
		List<Customer> customers = customerManager.customers(customer);
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Customer c : customers) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", c.getId());
			data.put("name", c.getName());
			data.put("surname01", c.getSurname01());			
			data.put("surname02", c.getSurname02());
			list.add(data);
		}		
		return list;
	}
	
	@RequestMapping(value="/viewOrders")
	public @ResponseBody List<Order> viewOrders(@RequestParam(value="idCustomer", required=true) Long idCustomer){
		logger.debug("idCustomer " + idCustomer);
		List<Order> orders = new ArrayList<Order>();
		Customer customer = new Customer();
		customer.setId(idCustomer);
		customer = customerManager.customers(customer).get(0);
		for (Ref<Order> myRef : customer.getOrders()) {
			orders.add(myRef.get());
		}
		return orders;
	}
	
}
