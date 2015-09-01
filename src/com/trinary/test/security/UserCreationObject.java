package com.trinary.test.security;

public class UserCreationObject {
	public String username;
	public String password;
	public String confirm;
	public String emailAddress;
	
	public UserCreationObject() {
		// TODO Auto-generated constructor stub
	}
	
	public UserCreationObject(String username, String password,
			String confirm, String emailAddress) {
		super();
		this.username = username;
		this.password = password;
		this.confirm = confirm;
		this.emailAddress = emailAddress;
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the confirm
	 */
	public String getConfirm() {
		return confirm;
	}
	/**
	 * @param confirm the confirm to set
	 */
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}