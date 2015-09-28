package com.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class SecurityController {

	private static final Logger logger = Logger.getLogger(SecurityController.class);
	
	@RequestMapping (value="/", method=RequestMethod.GET)
	public String goIndex(ModelMap modelMap){
		return "index";		
	}
	
	@RequestMapping (value="/login", method=RequestMethod.GET)
	public String goLogin(){
		logger.debug("goLoggin");
		return "login";
	}

	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout() {
		return "login"; 
	}

	@RequestMapping (value="/loginfailed", method=RequestMethod.GET)
	public String goLoginFailed(ModelMap modelMap){
		modelMap.addAttribute("error", "true");
		return "login";
	}
	
	@RequestMapping (value="/test", method=RequestMethod.GET)
	public String goTest(ModelMap modelMap){
		return "test";
	}

}
