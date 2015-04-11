package com.trinary.test.util;

import java.io.*;
import java.security.*;

public class GravatarUtil {
	public static String baseURL = "http://www.gravatar.com/avatar/";
	
	public static String getGravatarUrl(String emailAddress) {
		if (emailAddress == null) {
			emailAddress = "default";
		}
		return baseURL + md5Hex(emailAddress);
	}
	
	protected static String md5Hex(String message) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return hex(md.digest(message.getBytes("CP1252")));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
		}
		return null;
	}
	
	protected static String hex(byte[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; ++i) {
			sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
					.substring(1, 3));
		}
		return sb.toString();
	}
}