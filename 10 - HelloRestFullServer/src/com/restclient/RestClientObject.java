package com.restclient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.entities.EmployeeVO;

public class RestClientObject {

	public static void main(String[] args) {
		final String uri = "http://localhost:8080/helloRestFullServer/employees/1";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<EmployeeVO> forEntity = restTemplate.getForEntity(uri, EmployeeVO.class);
		System.out.println(forEntity.getStatusCode());
		System.out.println(forEntity.getBody().toString());
	}
}
