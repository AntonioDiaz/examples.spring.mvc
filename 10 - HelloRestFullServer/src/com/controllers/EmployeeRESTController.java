package com.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.entities.EmployeeVO;

@RestController
public class EmployeeRESTController {

	@RequestMapping(value="/employees/",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public List<EmployeeVO> listEmployees() {
		List<EmployeeVO> employees = new ArrayList<EmployeeVO>();
		employees.add(new EmployeeVO(1, "First", "Gupta", "howtodoinjava@gmail.com"));
		employees.add(new EmployeeVO(2, "Second", "Another", "howtodoinjava@gmail.com"));
		employees.add(new EmployeeVO(3, "Third", "Surname", "howtodoinjava@gmail.com"));
		return employees;
	}
	
	@RequestMapping(value = "/employees/{id}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeVO> getEmployeeById(@PathVariable("id") int id) {
		ResponseEntity<EmployeeVO> response;
		if (id <= 3) {
			EmployeeVO employee = new EmployeeVO(1, "Lokesh", "Gupta", "howtodoinjava@gmail.com");
			response = new ResponseEntity<EmployeeVO>(employee, HttpStatus.OK);
		} else {
			response = new ResponseEntity<EmployeeVO>(HttpStatus.NOT_FOUND);
		}
		return response;
	}
}
