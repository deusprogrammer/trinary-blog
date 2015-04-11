package com.trinary.test.security.apikey;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import com.trinary.test.persistence.entity.User;
import com.trinary.test.service.UserService;

public class ApiKeyAuthenticationProvider implements AuthenticationProvider {
	@Autowired UserService userService;

	/* (non-Javadoc)
	 * @see com.trinary.test.security.ApiKeyAuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		ApiKeyAuthentication apiKeyAuth = (ApiKeyAuthentication)auth;
		
		// Start off authenticated unless user is not found
		apiKeyAuth.setAuthenticated(true);
		
		// Get user from api key and populate auth object
		User user = null;
		try {
			if (apiKeyAuth.getCredentials() == null) {
				throw new Exception();
			}
			user = userService.getByApiKey((String)auth.getCredentials());
		} catch (Exception e) {
			// User not found
		}
		
		if (user == null) {
			user = User.ANONYMOUS;
			apiKeyAuth.setAuthenticated(false);
			apiKeyAuth.setAuthorities(Collections.<GrantedAuthority>emptyList());
		}
		
		apiKeyAuth.setPrincipal(user);
		
		return apiKeyAuth;
	}

	/* (non-Javadoc)
	 * @see com.trinary.test.security.ApiKeyAuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authClass) {
		return ApiKeyAuthentication.class.isAssignableFrom(authClass);
	}

}