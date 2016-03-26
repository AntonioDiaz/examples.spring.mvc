package com.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.forms.EntityForm;

@Component
public class EntityFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return EntityForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//EntityForm entityForm = (EntityForm)target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field_required");	
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "field_required");	
	}
}
