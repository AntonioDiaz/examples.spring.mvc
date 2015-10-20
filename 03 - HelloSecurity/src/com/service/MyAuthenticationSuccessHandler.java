package com.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.domain.User;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private static final Logger logger = Logger.getLogger(MyAuthenticationSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication)
			throws IOException, ServletException {
		logger.debug("onAuthenticationSuccess");
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.debug("user name -->" + user.getUsername());
		logger.debug("user authorities -->" + user.getAuthorities());
		httpServletResponse.sendRedirect("");
	}

}
