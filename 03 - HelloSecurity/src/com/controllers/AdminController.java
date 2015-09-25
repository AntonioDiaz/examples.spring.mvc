package com.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping ("/admin")
public class AdminController {

	/*private static final Logger logger = Logger.getLogger(AdminController.class);*/ 
	
	@RequestMapping (value="/userslist", method=RequestMethod.GET)
	public String goUsersList(){
		return "admin_users";
	}

	
}
