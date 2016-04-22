package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.log4j.Logger;

import com.services.PersonManager;
import com.services.PhoneManager;

@Controller
public class MyController {

	private static final Logger logger = Logger.getLogger(MyController.class);

	@Autowired private PersonManager personManager;
	@Autowired private PhoneManager phoneManager;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String helloJpa(ModelMap modelMap) {
		logger.debug("wellcomePage");
		modelMap.addAttribute("message", "Spring 3 MVC Hello World");		
		try {
			modelMap.addAttribute("people_list", personManager.queryPersonAll());
			modelMap.addAttribute("phones", phoneManager.queryPhoneAll());
		} catch (Exception e) {
			logger.error(e);
		}
		return "helloJPA";
	}
}
