package com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daos.PhoneDAO;
import com.persistence.Phone;

@Service
public class PhoneManagerImpl implements PhoneManager {

	@Autowired private PhoneDAO phoneDAO;
	
	@Override
	public void createPhone(Phone phone) throws Exception {
		phoneDAO.create(phone);
	}
}
