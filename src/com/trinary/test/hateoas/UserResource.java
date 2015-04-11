package com.trinary.test.hateoas;

import org.springframework.hateoas.ResourceSupport;

public class UserResource extends ResourceSupport {
	protected long id;
	protected String username;

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
	 * @return the id
	 */
	public long getUserId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setUserId(long id) {
		this.id = id;
	}
}