package com.trinary.test.controller.v2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trinary.persistence.OrderPair;
import com.trinary.test.hateoas.BlogResource;
import com.trinary.test.hateoas.BlogResourceAssembler;
import com.trinary.test.hateoas.CommentResource;
import com.trinary.test.hateoas.CommentResourceAssembler;
import com.trinary.test.hateoas.UserResource;
import com.trinary.test.hateoas.UserResourceAssembler;
import com.trinary.test.service.UserService;

@Controller
@RequestMapping(value="/user")
public class UserControllerV2 {
	@Autowired UserService userService;
	@Autowired protected BlogResourceAssembler blogAssembler;
	@Autowired protected UserResourceAssembler userAssembler;
	@Autowired protected CommentResourceAssembler commentAssembler;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	@ResponseBody
	public List<UserResource> getAll(
			@RequestParam(value = "page", required=false) Integer page, 
			@RequestParam(value = "pageSize", required=false) Integer pageSize,
			@RequestParam(value = "order", required=false) String orderBy) {
		return userAssembler.toResources(userService.getAll(page, pageSize, OrderPair.generateAll(orderBy)));
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	@ResponseBody
	public UserResource createUser() {
		return null;
	}
	
	@RequestMapping(value="/{userId}/blogs", method=RequestMethod.GET)
	@ResponseBody
	public List<BlogResource> getBlogs(
			@PathVariable("userId") Long userId) {
		return blogAssembler.toResources(userService.getBlogs(userId));
	}
	
	@RequestMapping(value="/{userId}/comments", method=RequestMethod.GET)
	@ResponseBody
	public List<CommentResource> getComments(
			@PathVariable("userId") Long userId,
			@RequestParam(value = "page", required=false) Integer page, 
			@RequestParam(value = "pageSize", required=false) Integer pageSize) {
		if (page == null || pageSize == null) {
			return commentAssembler.toResources(userService.getComments(userId));
		}
		try {
			return commentAssembler.toResources(userService.getComments(userId, page, pageSize));
		} catch (Exception e) {
			return null;
		}
	}
}
