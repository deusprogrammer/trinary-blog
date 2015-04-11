package com.trinary.test.security.token;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.trinary.test.service.UserService;

public class MemoryBasedTokenManager extends AbstractTokenManager implements TokenManager {
	@Autowired UserService userService;
	
	static Map<String, Token> tokenMap = new HashMap<String, Token>();
	static Map<String, Token> userMap  = new HashMap<String, Token>();

	@Override
	public Token createToken(Authentication authentication) throws ParseException {
		UserDetails user = (UserDetails)authentication.getPrincipal();
		user.getAuthorities().add(new GrantedAuthority() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getAuthority() {
				return "ROLE_USER";
			}
		});
		Token token = userMap.get(user.getUsername());
		
		if (token == null) {
			token = tokenFactory.createToken(user);
		}
		
		// Store the token in memory
		tokenMap.put(token.getToken(), token);
		userMap.put(token.getUser().getUsername(), token);
		
		return token;
	}

	@Override
	public Token authenticateToken(String tokenString) throws TokenExpiredException, TokenInvalidException {
		Token token = tokenMap.get(tokenString);
		
		if (token == null) {
			throw new TokenInvalidException("Access is denied.  Token invalid.");
		}
		
		if (token.isExpired()) {
			tokenMap.remove(token.getToken());
			userMap.remove(token.getUser().getUsername());
			throw new TokenExpiredException("Access is denied.  Token expired.");
		}
		
		return token;
	}

	@Override
	public Token releaseToken(String tokenString) {
		Token token = tokenMap.get(tokenString);
		
		if (token == null) {
			return null;
		}
		
		tokenMap.remove(token.getToken());
		userMap.remove(token.getUser().getUsername());
		
		return token;
	}
	
	@Override
	public void releaseAllExpiredTokens() {
		for (Entry<String, Token> entry : tokenMap.entrySet()) {
			Token token = entry.getValue();
			if (token.isExpired()) {
				tokenMap.remove(token.getToken());
				userMap.remove(token.getUser().getUsername());
			}
		}
	}
}