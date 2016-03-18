package com;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class MyController {

	@Value("${mail_user}")
	String mailUser;
	@Value("${mail_password}")
	String mailPassword;
	
	@RequestMapping(value="/helloWeb", method=RequestMethod.GET)
	public String wellcomePage(ModelMap modelMap) {
		modelMap.addAttribute("mail_user", mailUser);
		modelMap.addAttribute("mail_password", mailPassword);
		modelMap.addAttribute("message", "Spring 3 MVC Hello World");
		return "hello";
	}
	
}
