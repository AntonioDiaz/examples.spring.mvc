package com.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ControllerTesting {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String wellcomePage(ModelMap modelMap) {
		modelMap.addAttribute("message", "Spring 3 MVC Hello World");
		return "home";
	}
}