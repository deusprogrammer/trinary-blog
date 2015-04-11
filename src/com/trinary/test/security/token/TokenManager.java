package com.trinary.test.security.token;

import java.text.ParseException;

import org.springframework.security.core.Authentication;

public interface TokenManager {
	Token createToken(Authentication authentication) throws ParseException;
	Token authenticateToken(String token) throws TokenExpiredException, TokenInvalidException;
	Token releaseToken(String token);
	void releaseAllExpiredTokens();
	void setTokenFactory(TokenFactory tokenFactory);
	TokenFactory getTokenFactory();
}