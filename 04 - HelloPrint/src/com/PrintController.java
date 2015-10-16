package com;

import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PrintController {

	@RequestMapping(value = "/getPdf", method = RequestMethod.GET)
	public ModelAndView getPdf() {
		ModelAndView modelAndView = new ModelAndView("pdfView");
		String[] libros = new String[]{"El Quijote", "La celestina"};		
		modelAndView.addObject("libros_list", Arrays.asList(libros));
		return modelAndView;
	}

	@RequestMapping(value = "/helloWeb", method = RequestMethod.GET)
	public String wellcomePage(ModelMap modelMap) {
		modelMap.addAttribute("message", "Imprime coone");
		return "hello";
	}
}
