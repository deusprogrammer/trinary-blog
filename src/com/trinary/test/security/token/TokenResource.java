package com.trinary.test.security.token;

import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

import com.trinary.test.hateoas.UserResource;

public class TokenResource extends ResourceSupport {
	protected String token;
	protected Date expires;
	protected UserResource user;
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the expires
	 */
	public Date getExpires() {
		return expires;
	}
	/**
	 * @param expires the expires to set
	 */
	public void setExpires(Date expires) {
		this.expires = expires;
	}
	/**
	 * @return the user
	 */
	public UserResource getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(UserResource user) {
		this.user = user;
	}
}