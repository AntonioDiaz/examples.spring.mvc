package com.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.forms.EntityForm;
import com.validators.EntityFormValidator;

@Controller
public class HelloFormsController {

	private static final Logger logger = Logger.getLogger(HelloFormsController.class);

	@Autowired
	private EntityFormValidator entityFormValidator;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView newEntity(@RequestParam(required=false)boolean created) {
		logger.debug("newentity");
		ModelAndView modelAndView = new ModelAndView("entity_new");
		modelAndView.addObject("my_form", new EntityForm());
		if (created) {
			modelAndView.addObject("result", "ok");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/doCreateEntity", method = RequestMethod.POST)
	public String createEntity(@ModelAttribute("my_form") EntityForm entityForm, BindingResult bindingResult, ModelMap modelMap) {
		logger.debug("createEntity -> " + entityForm);
		String next;
		this.entityFormValidator.validate(entityForm, bindingResult);
		if (!bindingResult.hasErrors()) {
			next = "redirect:/?created=true";
		} else {
			if (entityForm.getBirthdate()!=null) {
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
				modelMap.addAttribute("birthdate_str",dateFormat.format(entityForm.getBirthdate()));
			}
			next = "entity_new";
		}
		return next;

	}

}
