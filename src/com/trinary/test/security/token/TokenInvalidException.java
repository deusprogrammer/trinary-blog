package com.trinary.test.security.token;

import org.springframework.security.access.AccessDeniedException;

public class TokenInvalidException extends AccessDeniedException {
	public TokenInvalidException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = 1L;

}
