package com.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.forms.EntityForm;
import com.validators.EntityFormValidator;

@Controller
@RequestMapping("/forms")
public class HelloFormsController {

	private static final Logger logger = Logger.getLogger(HelloFormsController.class);

	@Autowired
	private EntityFormValidator entityFormValidator;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/newentity", method = RequestMethod.GET)
	public ModelAndView newEntity() {
		logger.debug("newentity");
		ModelAndView modelAndView = new ModelAndView("entity_new");
		EntityForm entityForm = new EntityForm();
		modelAndView.addObject("my_form", entityForm);
		return modelAndView;
	}

	@RequestMapping(value = "/doCreateEntity", method = RequestMethod.POST)
	public String createEntity(@ModelAttribute("my_form") EntityForm entityForm, BindingResult bindingResult) {
		logger.debug("createEntity -> " + entityForm);
		String result;
		this.entityFormValidator.validate(entityForm, bindingResult);
		if (bindingResult.hasErrors()) {
			result = "entity_new";
		} else {
			result = "entity_created";
		}
		return result;

	}

}
