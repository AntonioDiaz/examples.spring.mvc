## Rest with Spring MVC 4

Proof of concept of a Rest service using Spring MVC 4.

Thanks to: <a href="http://howtodoinjava.com/spring/spring-restful/spring-rest-hello-world-json-example/" target="_blank">http://howtodoinjava.com/spring/spring-restful/spring-rest-hello-world-json-example/</a>

* [Project structure](#project-structure)
* [Call example](#call-example)
* [pom.xml](#pomxml)  
* [applicationContext.xml](#applicationcontextxml)  
* [EmployeeRESTController.java](#employeerestcontrollerjava)  
* [Rest client](#rest-client)  

***
### Project structure  
![Project structure](https://antoniodiaz.github.io/images/spring_mvc/10_structure.jpg "project structure")

***
### Call example
![](https://antoniodiaz.github.io/images/spring_mvc/10_call_example.jpg)

***
### pom.xml
``` xml
<properties>
	<spring.version>4.1.4.RELEASE</spring.version>
	<jstl.version>1.2</jstl.version>
</properties>
<dependencies>
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-webmvc</artifactId>
		<version>${spring.version}</version>
	</dependency>
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-web</artifactId>
		<version>${spring.version}</version>
	</dependency>
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-core</artifactId>
		<version>${spring.version}</version>
	</dependency>
	<dependency>
		<groupId>javax.servlet</groupId>
		<artifactId>jstl</artifactId>
		<version>${jstl.version}</version>
	</dependency>
	<dependency>
		<groupId>com.fasterxml.jackson.core</groupId>
		<artifactId>jackson-databind</artifactId>
		<version>2.8.5</version>
	</dependency>
</dependencies>
```

***
### applicationContext.xml
``` xml
<context:component-scan base-package="com" />
<mvc:annotation-driven></mvc:annotation-driven>
```

***
### EmployeeRESTController.java
``` java
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
```  
***
### REST client
RestTemplate example:
``` java
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.entities.EmployeeVO;

public class RestClientList {

	public static void main(String[] args) {
		final String uri = "http://localhost:8080/helloRestFullServer/employees/";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<EmployeeVO[]> forEntity = restTemplate.getForEntity(uri, EmployeeVO[].class);
		System.out.println(forEntity.getStatusCode());
		System.out.println(forEntity.getBody().length);
	}
}
```
