package com.trinary.test.security;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

import com.trinary.test.security.token.TokenAuthentication;
import com.trinary.test.util.StringUtils;

public class AuthenticationFactoryImpl implements AuthenticationFactory {

	@Override
	public Authentication createAuthentication(HttpServletRequest request) {
		AuthorizationHeader authorization = parseHeader(request);
		if (authorization != null && authorization.type.toUpperCase().equals("TOKEN")) {
			return new TokenAuthentication(authorization.getMap().get("token"));
		} else {
			return new TokenAuthentication("");
		}
	}
	
	protected AuthorizationHeader parseHeader(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		String[] authHeaderPair = new String[2];
		
		if (authHeader != null) {
			authHeaderPair = authHeader.split(" ", 2);
			
			if (authHeaderPair.length >= 2) {
				return new AuthorizationHeader(authHeaderPair[0], authHeaderPair[1]);
			}
		}
		
		return null;
	}

	protected class AuthorizationHeader {
		protected String type;
		protected Map<String, String> map;
		
		public AuthorizationHeader(String type, String authorizations) {
			super();
			this.type = type;
			this.map = new HashMap<String, String>();
			
			this.map = StringUtils.stringToMap(authorizations, ",", "=");
		}
		
		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}
		/**
		 * @param type the type to set
		 */
		public void setType(String type) {
			this.type = type;
		}
		/**
		 * @return the map
		 */
		public Map<String, String> getMap() {
			return map;
		}
		/**
		 * @param map the map to set
		 */
		public void setMap(Map<String, String> map) {
			this.map = map;
		}
	}
}