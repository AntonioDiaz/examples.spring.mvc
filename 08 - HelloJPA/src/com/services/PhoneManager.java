package com.services;

import java.util.List;

import com.persistence.Phone;

public interface PhoneManager {
	public List<Phone> queryPhoneAll() throws Exception;
	public void createPhone(Phone phone) throws Exception;
	public void updatePhone(Phone phone) throws Exception;
}
