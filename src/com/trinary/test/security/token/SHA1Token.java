package com.trinary.test.security.token;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.core.userdetails.UserDetails;

public class SHA1Token extends Token {
	public SHA1Token(UserDetails user) throws ParseException {
		super(user);
	}

	@Override
	protected String generateToken(UserDetails user) {
		Date now = new Date();
		StringBuilder sb = new StringBuilder();
		sb.append(user.getUsername());
		sb.append(":");
		sb.append(user.getPassword());
		sb.append(":");
		sb.append(now);
		sb.append(":");
		sb.append(UUID.randomUUID().toString());
		
		String token = null;
		try {
			token = Base64.encodeBase64String(
					MessageDigest.getInstance("SHA-1").digest(sb.toString().getBytes()))
					.replaceAll("=", "");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return token;
	}
}