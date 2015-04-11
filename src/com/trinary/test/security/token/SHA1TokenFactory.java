package com.trinary.test.security.token;

import java.text.ParseException;

import org.springframework.security.core.userdetails.UserDetails;

public class SHA1TokenFactory implements TokenFactory {

	@Override
	public Token createToken(UserDetails userDetails) throws ParseException {
		return new SHA1Token(userDetails);
	}
}