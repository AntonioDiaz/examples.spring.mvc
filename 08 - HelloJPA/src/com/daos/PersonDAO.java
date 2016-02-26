package com.daos;

import java.util.List;

import com.persistence.Person;

public interface PersonDAO extends GenericDAO<Person> {

	public List<Person> getPersonAll();
	
	public Person getPersonById(Long id);
	
	public Person getPersonByIdJoin(Long id);
	
	public List<Person> getPersonByNameEqual(String name);
	
	public List<Person> getPersonByNameLike(String name);

	
}
