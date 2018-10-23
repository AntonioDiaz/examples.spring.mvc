## How submit a form using Spring MVC
* [Project structure](#project-structure)
* [Front End](#front-end)
* [Controller](#controller)
* [form](#form)
* [JSP](#jsp)
* [Properties](#properties)
* [Bootstrap datepicker](#bootstrap-datepicker)  
* [Bootstrap pop-up](#bootstrap-pop-up)


***
### Project structure  
![](https://antoniodiaz.github.io/images/spring_mvc/02_structure.jpg)

***
### Front End
![](https://antoniodiaz.github.io/images/spring_mvc/02_front_end.jpg)

***

### Controller
EntityFormsController.java  
@Autowired --> Its necesary to wire the validator.  
@InitBinder --> to set date format.  
``` java
	...
	@Autowired
	private EntityFormValidator entityFormValidator;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
```

***
### Form
Declare a form (EntityForm.java)
The form object is a normal bean, with private attributes, getters, setters and constructors.  
``` java
	...
	private Long id;
	private String name;
	private String surname;
	private Date birthdate;
```
***
### JSP
Using form in the JSP
``` html
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
...
<form:form method="post" action="doCreateEntity" commandName="my_form" cssClass="form-horizontal">
  Nombre: <form:input path="name" cssClass="xlarge form-control" size="30" placeholder="Entity name" />
  <form:errors path="name" cssClass="help-inline" />
</form:form>       
```
***
### Validator  
@Component --> to allow Spring to wire this class.  
``` java
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
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name_required");
	}
}

```
***
### properties
Using properties file to show errors  
Add this bean to applicationContext.xml
``` xml
	...
	<bean class="org.springframework.context.support.ReloadableResourceBundleMessageSource" id="messageSource"
		p:basenames="WEB-INF/i18n/messages" p:fallbackToSystemLocale="false" />
```  

***
### Bootstrap datepicker
[https://eonasdan.github.io/bootstrap-datetimepicker/](https://eonasdan.github.io/bootstrap-datetimepicker/)  
* Import javacript and css:  
``` xml
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<!-- bootstrap-datetimepicker  -->
<script type="text/javascript" src="resources/js/moment.min.js"></script>
<script type="text/javascript" src="resources/js/bootstrap-datetimepicker.min.js"></script>
<link rel="stylesheet" href="resources/css/bootstrap-datetimepicker.css" />
```
* Declare input:
``` html
<div class="col-sm-6">
	Birthdate
	<div class='input-group date' id='datetimepicker1'>
		<!-- <form:input type="text" path="birthdate" cssClass="xlarge form-control" size="30" />-->
		<input type='text' class="form-control" id="birthdate" name="birthdate" />
			<span class="input-group-addon">
				<span class="glyphicon glyphicon-calendar"></span>
			</span>
	</div>
</div>
<script type="text/javascript">
	$(function () {
		$('#datetimepicker1').datetimepicker({
			defaultDate: "${birthdate_str}"
		});
	});
</script>
```
* java parse string to date in controller:  
``` java
@InitBinder("my_form")
public void initBinder(WebDataBinder webDataBinder) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
	dateFormat.setLenient(false);
	webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
}
```
* java code to init field:
``` java
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
```

***
### Bootstrap pop-up
Declare html:
``` html
<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
	<div class="modal-dialog modal-sm">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Entity Created</h4>
			</div>
			<div class="modal-body">
				<div id="modalContent"></div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>
```
* javascript to show the pop-up
``` javascript
<script type="text/javascript">
	$(document).ready(function() {
		<c:if test='${result=="ok"}'>
			$('#modalContent').html('Everything works fine.');
			$('#myModal').modal('show');
		</c:if>
	});
</script>
```
