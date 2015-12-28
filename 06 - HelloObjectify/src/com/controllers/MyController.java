package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.services.CustomerManager;

@Controller
public class MyController {

	@Autowired
	CustomerManager customerManager;

	@RequestMapping(value = "/customersList", method = RequestMethod.GET)
	public String customerList(ModelMap modelMap) {
		modelMap.addAttribute("customerList", customerManager.customers());
		return "customers";
	}

}
