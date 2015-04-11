package com.trinary.test.security.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class TokenAuthenticationProvider implements AuthenticationProvider {
	@Autowired TokenManager tokenManager;
	
	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		Token token;
		try {
			token = tokenManager.authenticateToken((String)auth.getCredentials());
		} catch (TokenInvalidException e) {
			return auth;
		}
		
		TokenAuthentication newAuth = new TokenAuthentication(token);
		
		return newAuth;
	}

	@Override
	public boolean supports(Class<? extends Object> authClass) {
		return TokenAuthentication.class.isAssignableFrom(authClass);
	}

}
