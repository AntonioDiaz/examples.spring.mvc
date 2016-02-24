package com;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.apache.log4j.Logger;


@Controller
public class MyController {

	 private static final Logger logger = Logger.getLogger(MyController.class);
	
	@RequestMapping(value="/helloWeb", method=RequestMethod.GET)	
	public String wellcomePage(ModelMap modelMap) {
		logger.debug("wellcomePage");
		modelMap.addAttribute("message", "Spring 3 MVC Hello World");
		return "hello";
	}
}
