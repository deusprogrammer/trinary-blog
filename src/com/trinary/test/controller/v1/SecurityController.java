package com.trinary.test.controller.v1;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trinary.test.hateoas.UserResourceAssembler;
import com.trinary.test.persistence.entity.User;
import com.trinary.test.security.AuthObject;
import com.trinary.test.security.AuthStatus;
import com.trinary.test.security.token.Token;
import com.trinary.test.security.token.TokenExpiredException;
import com.trinary.test.security.token.TokenInvalidException;
import com.trinary.test.security.token.TokenManager;
import com.trinary.test.security.token.TokenResource;
import com.trinary.test.service.UserService;

@Controller
@RequestMapping(value="/security")
public class SecurityController {
	@Autowired TokenManager tokenManager;
	@Autowired AuthenticationManager authenticationManager;
	@Autowired UserService userService;
	@Autowired UserResourceAssembler userAssembler;
	
	@RequestMapping(value="/deauth", method=RequestMethod.POST)
	@ResponseBody
	public TokenResource deauthenticate(HttpServletResponse response, @RequestBody TokenResource tokenResource) {
		Token updatedToken = tokenManager.releaseToken(tokenResource.getToken());
		TokenResource updatedTokenResource = new TokenResource();
		updatedTokenResource.setToken(updatedToken.getToken());
		updatedTokenResource.setExpires(updatedToken.getExpires());
		updatedTokenResource.setUser(userAssembler.toResource((User)updatedToken.getUser()));
		
		return updatedTokenResource;
	}
	
	@RequestMapping(value="/reauth", method=RequestMethod.POST)
	@ResponseBody
	public TokenResource reauthenticate(HttpServletResponse response, @RequestBody TokenResource tokenResource) throws TokenExpiredException, TokenInvalidException {
		Token updatedToken = tokenManager.authenticateToken(tokenResource.getToken());
		TokenResource updatedTokenResource = new TokenResource();
		updatedTokenResource.setToken(updatedToken.getToken());
		updatedTokenResource.setExpires(updatedToken.getExpires());
		updatedTokenResource.setUser(userAssembler.toResource((User)updatedToken.getUser()));
		
		return updatedTokenResource;
		
	}
	
	@RequestMapping(value="/auth", method=RequestMethod.POST)
	@ResponseBody
	public TokenResource authenticate(HttpServletResponse response, @RequestBody AuthObject authObject) throws Exception {
		User details = userService.getByUsername(authObject.username);
		UsernamePasswordAuthenticationToken userPasswordToken = new UsernamePasswordAuthenticationToken(authObject.username, authObject.password);
	    userPasswordToken.setDetails(details);
	    
	    Authentication auth = null;
	    try {
		    auth = authenticationManager.authenticate(userPasswordToken);
	    } catch (BadCredentialsException e) {
	    	response.setStatus(405);
	    	return null;
	    }
	    
	    Token token = tokenManager.createToken(auth);
	    
	    if (token == null) {
	    	response.setStatus(500);
	    	return null;
	    }
	    
	    TokenResource tokenResource = new TokenResource();
	    tokenResource.setToken(token.getToken());
	    tokenResource.setExpires(token.getExpires());
	    tokenResource.setUser(userAssembler.toResource((User)token.getUser()));
	    
	    return tokenResource;
	}
	
	@RequestMapping(value="/principal")
	@ResponseBody
	public AuthStatus getCurrentPrincipal() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getPrincipal() instanceof String) {
			return new AuthStatus(false, null);
		}
		User user = (User)auth.getPrincipal();
		return new AuthStatus(true, userAssembler.toResource(user));
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseBody
	public Map<String, String> handleAccessDeniedException(HttpServletResponse response, Exception e){
		response.setStatus(401);
		
		Map<String, String> errorMap = new HashMap<String, String>();
		errorMap.put("error", e.getClass().toString());
		errorMap.put("message", e.getMessage());
		
		e.printStackTrace();
		
		return errorMap;
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Map<String, String> handleGenericException(HttpServletResponse response, Exception e){
		response.setStatus(500);
		
		Map<String, String> errorMap = new HashMap<String, String>();
		errorMap.put("error", e.getClass().toString());
		errorMap.put("message", e.getMessage());
		
		e.printStackTrace();
		
		return errorMap;
	}
}