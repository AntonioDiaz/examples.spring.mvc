package com.service;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.domain.User;

@Service ("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	Logger logger = Logger.getLogger(CustomUserDetailsService.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("username: " + username);
		/** should find user by "username". */ 
		User user = new User();
		user.setUsername("adiaz");
		user.setEnabled(true);
		user.setBannedUser(false);
		user.setAccountNonExpired(true);
		/** password:  "admin" */
		user.setPassword("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");
		user.setAdmin(true);
		return user;
	}

}

