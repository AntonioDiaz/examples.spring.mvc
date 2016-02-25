package com.utilities;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.persistence.Person;
import com.services.PersonManager;

public class InitDataBase {

	private static final Logger logger = Logger.getLogger(InitDataBase.class);

	@Autowired
	private PersonManager personManager;	
	
	public void init() {
		logger.debug("init DATABASE");
		Person person = new Person();
		try {
			person.setFirstName("Lobezno");
			person.setLastName("Muñoz Torrijos");
			personManager.createUser(person);
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
