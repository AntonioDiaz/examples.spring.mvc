package com;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MyController {

	@Value("${mail_user}")
	String mailUser;
	@Value("${mail_password}")
	String mailPassword;

	private static final Logger logger = Logger.getLogger(MyController.class);
	
	@RequestMapping(value="/helloWeb", method=RequestMethod.GET)
	public String wellcomePage(ModelMap modelMap) {
		logger.debug("helloWeb modelMap -->" +  modelMap);
		modelMap.addAttribute("mail_user", mailUser);
		modelMap.addAttribute("mail_password", mailPassword);
		modelMap.addAttribute("message", "Spring 3 MVC Hello World");
		return "hello";
	}
	
	@RequestMapping(value="/thowsException", method=RequestMethod.GET)
	public String throwsException(ModelMap modelMap) throws Exception {
		String str = null;
		//must throw an exception. 
		logger.debug(str.length());
		return "doesntreachhere";
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(HttpServletRequest req,Exception exception) {
	    logger.error("Request: " + req.getRequestURL() + " raised " + exception);
	    ModelAndView mav = new ModelAndView();
	    mav.addObject("exception", exception);
	    mav.addObject("url", req.getRequestURL());
	    mav.setViewName("error");
	    return mav;
	}
	
}
