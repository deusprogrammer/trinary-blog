package com.trinary.test.security.apikey;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ApiKeySecurityFilter implements Filter {
	@Autowired AuthenticationManager authenticationManager;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String authHeader = httpRequest.getHeader("Authorization");
		String[] authHeaderPair = new String[2];
		String apiKey = null;
		
		if (authHeader != null) {
			authHeaderPair = authHeader.split(" ", 2);
			
			if (authHeaderPair[0].trim().toUpperCase().equals("API-KEY")) {
				apiKey = authHeaderPair[1].trim();
			}
		}
		
		ApiKeyAuthentication authentication = new ApiKeyAuthentication();
		authentication.setCredentials(apiKey);
		
		Authentication auth = authenticationManager.authenticate(authentication);
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		chain.doFilter(httpRequest, httpResponse);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}