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
		User user = new User();
		user.setUsername("adiaz");
		user.setEnabled(true);
		user.setBannedUser(false);
		user.setAccountNonExpired(true);
		user.setPassword("bb37067afeb4ee16d668eef073ca6eea4f3b4a1fc6c68e3c0b1fd01a5fb7f5ad");
		user.setAdmin(true);
		return user;
	}

}

