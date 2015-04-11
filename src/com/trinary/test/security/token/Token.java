package com.trinary.test.security.token;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

public abstract class Token {
	protected String token;
	protected UserDetails user;
	protected Date expires;
	
	public Token(UserDetails user) throws ParseException {
		this(user, "8 hours");
	}
	
	public Token(UserDetails user, String expiresIn) throws ParseException {
		this.user = user;
		this.token = generateToken(user);
		this.expires = getLaterDate(expiresIn);
	}

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
	 * @return the user
	 */
	public UserDetails getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(UserDetails user) {
		this.user = user;
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
	
	public boolean isExpired() {
		Date now = new Date();
		return expires.before(now);
	}
	
	protected Date getLaterDate(String duration) throws ParseException {
		return getLaterDate(new Date(), duration);
	}
	
	protected Date getLaterDate(Date start, String duration) throws ParseException {
		String[] parts = duration.split(" ");
		
		if (parts.length < 2) {
			throw new ParseException("Unable to parse time duration", 0);
		}
		
		Integer number = Integer.parseInt(parts[0]);
		String  unitOfTime = parts[1];
		
		Integer multiplier = 1;
		switch(unitOfTime) {
		case "milliseconds":
			multiplier *= 1;
			break;
		case "seconds":
			multiplier *= 1000;
			break;
		case "minutes":
			multiplier *= 1000 * 60;
			break;
		case "hours":
			multiplier *= 1000 * 60 * 60;
			break;
		case "days":
			multiplier *= 1000 * 60 * 60 * 24;
			break;
		case "weeks":
			multiplier *= 1000 * 60 * 60 * 24 * 7;
			break;
		case "months":
			multiplier *= 1000 * 60 * 60 * 24 * 7 * 4;
			break;
		case "years":
			multiplier *= 1000 * 60 * 60 * 24 * 7 * 4 * 12;
			break;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(start.getTime() + (number * multiplier));
		return calendar.getTime();
	}
	
	protected abstract String generateToken(UserDetails user);
}