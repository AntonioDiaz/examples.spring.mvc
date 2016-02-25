package com.services;

import java.util.List;

import com.persistence.Person;

public interface PersonManager {

	public void createUser(Person person) throws Exception;
	
	public List<Person> queryPersonAll() throws Exception;
	
	public Person queryPersonById(Long id) throws Exception;
	
	
	public List<Person> queryPersonByNameEqual(String name) throws Exception;
	
	
}
