package com.daos;

import java.util.List;

import com.persistence.Phone;

public interface PhoneDAO extends GenericDAO<Phone> {
	
	
	public List<Phone> getPhoneAll();

}
