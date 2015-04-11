package com.trinary.test.security.token;

import java.text.ParseException;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenFactory {
	Token createToken(UserDetails userDetails) throws ParseException;
}