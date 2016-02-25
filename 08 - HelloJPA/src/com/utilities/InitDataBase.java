package com.utilities;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.persistence.IdCard;
import com.persistence.Person;
import com.services.PersonManager;

public class InitDataBase {

	private static final Logger logger = Logger.getLogger(InitDataBase.class);

	@Autowired
	private PersonManager personManager;	
	
	public void init() {
		logger.debug("init DATABASE");
		Person person = new Person();
		IdCard idCard = new IdCard();
		try {
			idCard.setIdNumber("1234-1234-1234-1234");
			idCard.setIssueDate(new Date());
			person.setFirstName("Lobezno");
			person.setLastName("Muñoz Torrijos");
			person.setIdCard(idCard);
			personManager.createUser(person);
			
			logger.debug("Person 1" + personManager.queryPersonById(new Long(1)));		
			logger.debug("Person 2" + personManager.queryPersonById(new Long(2)));		
			
		} catch (Exception e) {
			logger.error(e);
		}
		
	}
}
