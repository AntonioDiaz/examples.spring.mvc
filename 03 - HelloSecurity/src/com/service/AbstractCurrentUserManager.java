package com.service;

import org.springframework.security.core.context.SecurityContextHolder;
import com.domain.User;

public abstract class AbstractCurrentUserManager implements CurrentUserManager {
	
	@Override
	public User getEnabledUser() {
		User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return principal;
	}

}
