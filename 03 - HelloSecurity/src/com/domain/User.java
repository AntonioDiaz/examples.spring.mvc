package com.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private boolean admin;
	private boolean enabled;
	private boolean bannedUser;
	private boolean accountNonExpired;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
		authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
		if (admin) {
			authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		return authorityList;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !bannedUser;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isBannedUser() {
		return bannedUser;
	}

	public void setBannedUser(boolean bannedUser) {
		this.bannedUser = bannedUser;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	@Override
	public String toString() {
	    return ToStringBuilder.reflectionToString(this);
	}
}
