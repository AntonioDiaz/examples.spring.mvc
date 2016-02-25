package com.services;

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
}
