package com.trinary.test.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

public interface AuthenticationFactory {
	public Authentication createAuthentication(HttpServletRequest request);
}