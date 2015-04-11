package com.trinary.test.util;

import java.lang.reflect.Method;

import org.springframework.web.bind.annotation.RequestMapping;

public class TemplatedURIBuilder {
	public static String getTemplatedLink(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
		Method method;
		try {
			method = clazz.getMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			return "";
		}
		
		RequestMapping annotation = method.getAnnotation(RequestMapping.class);
		String[] values = annotation.value();
		
		return values.length > 0 ? values[0] : null;
	}
}