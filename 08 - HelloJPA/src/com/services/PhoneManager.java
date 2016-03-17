package com.services;

import com.persistence.Phone;

public interface PhoneManager {
	public void createPhone(Phone phone) throws Exception;
	public void updatePhone(Phone phone) throws Exception;
}
