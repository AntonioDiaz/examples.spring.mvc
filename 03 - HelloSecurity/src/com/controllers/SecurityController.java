package com.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class SecurityController {

	private static final Logger logger = Logger.getLogger(SecurityController.class);
	
	@RequestMapping (value="/login", method=RequestMethod.GET)
	public String goLogin(){
		logger.debug("goLoggin");
		return "login";
	}
	
}
