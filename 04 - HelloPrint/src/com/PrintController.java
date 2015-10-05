package com;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PrintController {

	@RequestMapping(value = "/getPdf", method = RequestMethod.GET)
	public ModelAndView getPdf() {
		return new ModelAndView("pdfView", "listBooks", null);
	}

	@RequestMapping(value = "/helloWeb", method = RequestMethod.GET)
	public String wellcomePage(ModelMap modelMap) {
		modelMap.addAttribute("message", "Imprime coone");
		return "hello";
	}
}
