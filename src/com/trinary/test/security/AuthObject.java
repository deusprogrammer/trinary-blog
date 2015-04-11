package com.trinary.test.security;

public class AuthObject {
	public String username;
	public String password;
	public String signature;
	
	public AuthObject() {
		// TODO Auto-generated constructor stub
	}
	
	public AuthObject(String username, String password, String signature) {
		super();
		this.username = username;
		this.password = password;
		this.signature = signature;
	}
}