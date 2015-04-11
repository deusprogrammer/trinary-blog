package com.trinary.test.security.apikey;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.trinary.test.persistence.entity.User;

public class ApiKeyAuthentication implements Authentication {
	private static final long serialVersionUID = 1L;
	
	protected String username;
	protected String apiKey;
	
	protected User principal;
	protected Map<String, Object> details;
	protected List<GrantedAuthority> authorities;
	
	protected Boolean isAuthenticated = false;

	/**
	 * @param principal the principal to set
	 */
	public void setPrincipal(User principal) {
		this.principal = principal;
	}

	@Override
	public String getName() {
		return username;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	public void setCredentials(Object apiKey) {
		this.apiKey = (String)apiKey;
	}

	@Override
	public Object getCredentials() {
		return apiKey;
	}

	@Override
	public Object getDetails() {
		return details;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	@Override
	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * @param apiKey the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * @return the isAuthenticated
	 */
	public Boolean getIsAuthenticated() {
		return isAuthenticated;
	}

	/**
	 * @param isAuthenticated the isAuthenticated to set
	 */
	public void setIsAuthenticated(Boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	/**
	 * @param details the details to set
	 */
	public void setDetails(Map<String, Object> details) {
		this.details = details;
	}

	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.isAuthenticated = isAuthenticated;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

}
