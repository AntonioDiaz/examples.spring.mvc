package com.utilities;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.persistence.IdCard;
import com.persistence.Person;
import com.persistence.Phone;
import com.services.PersonManager;
import com.services.PhoneManager;

public class InitDataBase {

	private static final Logger logger = Logger.getLogger(InitDataBase.class);

	@Autowired private PersonManager personManager;
	@Autowired private PhoneManager phoneManager;

	public void init() {
		logger.info("init DATABASE");
		Person person = new Person();
		IdCard idCard = new IdCard();
		try {
			idCard.setIdNumber("1234-1234-1234-1234");
			idCard.setIssueDate(new Date());
			
			person.setFirstName("Lobezno");
			person.setLastName("Muñoz Torrijos");
			person.setIdCard(idCard);
			personManager.createUser(person);
			try {
				logger.info("query-1 " + personManager.queryPersonById(new Long(1)));
				logger.info("query-2 " + personManager.queryPersonById(new Long(2)));
				logger.info("query-3 " + personManager.queryPersonByNameEqual("Lobezno"));
				logger.info("query-4 " + personManager.queryPersonByNameEqual("xxxx"));			
				logger.info("query-5 " + personManager.queryPersonByNameLike("lob"));
			} catch (Exception e) {
				e.printStackTrace();
			}			
			/* add phone */
			person = personManager.queryPersonById(new Long(1));
			Phone phone = new Phone();
			phone.setNumber("656-654-645");
			phone.setPerson(person);
			phoneManager.createPhone(phone);
			try {
				logger.info("query-6 " + personManager.queryPersonById(new Long(1)));
			} catch (Exception e) {
				e.printStackTrace();
			}			
			logger.info("query-7 " + personManager.queryPersonByIdJoin(new Long(1)));
			Person queryPersonByPhone = personManager.queryPersonByPhone("1234-1234-1234-1234");
			logger.info("query-8 " + queryPersonByPhone);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
}
