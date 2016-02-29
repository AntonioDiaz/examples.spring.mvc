package com.services;

import java.util.List;

import com.persistence.Person;

public interface PersonManager {

	public void createUser(Person person) throws Exception;
	
	public void updteUser(Person person) throws Exception;
	
	public List<Person> queryPersonAll() throws Exception;
	
	public Person queryPersonById(Long id) throws Exception;
	
	public Person queryPersonByIdJoin(Long id) throws Exception;
	
	public Person queryPersonByPhone(String myPhone) throws Exception;
	
	public Person queryPersonByPhoneCriteria(String myPhone) throws Exception;
	
	public List<Person> queryPersonByNameEqual(String name) throws Exception;

	public List<Person> queryPersonByNameLike(String name) throws Exception;


}
