package com.trinary.test.security;

import com.trinary.test.hateoas.UserResource;

public class AuthStatus {
	public Boolean authenticated;
	public UserResource principal;
	
	public AuthStatus() {
		// TODO Auto-generated constructor stub
	}
	
	public AuthStatus(Boolean authenticated, UserResource principal) {
		super();
		this.authenticated = authenticated;
		this.principal = principal;
	}
}