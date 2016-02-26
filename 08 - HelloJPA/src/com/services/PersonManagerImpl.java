package com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daos.PersonDAO;
import com.persistence.Person;

@Service
public class PersonManagerImpl implements PersonManager {

	@Autowired private PersonDAO personDAO;
	
	@Override
	public void createUser(Person person) throws Exception {
		personDAO.create(person);
	}

	@Override
	public List<Person> queryPersonAll() throws Exception {
		return personDAO.getPersonAll();
	}

	@Override
	public Person queryPersonById(Long id) throws Exception {
		return personDAO.getPersonById(id);
	}
	
	@Override
	public Person queryPersonByIdJoin(Long id) throws Exception {
		return personDAO.getPersonByIdJoin(id);
	}

	@Override
	public List<Person> queryPersonByNameEqual(String name) throws Exception {
		return personDAO.getPersonByNameEqual(name);
	}
	
	@Override
	public List<Person> queryPersonByNameLike(String name) throws Exception {
		return personDAO.getPersonByNameLike(name);
	}

	@Override
	public void updteUser(Person person) throws Exception {
		personDAO.update(person);
	}

	@Override
	public Person queryPersonByPhone(String myPhone) throws Exception {
		Person person = null;
		List<Person> persons = personDAO.getPersonsByPhone(myPhone);
			if (persons.size() > 1) {
				throw new Exception("More than one person.");
			} else if (persons.size()==0) {
				person = persons.get(0);
			}
		return person;
	}
}
