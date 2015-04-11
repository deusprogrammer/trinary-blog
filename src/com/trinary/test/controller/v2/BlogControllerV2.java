package com.trinary.test.controller.v2;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.trinary.persistence.OrderPair;
import com.trinary.test.hateoas.BlogResource;
import com.trinary.test.hateoas.BlogResourceAssembler;
import com.trinary.test.hateoas.CommentResource;
import com.trinary.test.hateoas.CommentResourceAssembler;
import com.trinary.test.hateoas.ResourceList;
import com.trinary.test.hateoas.ResourceListBuilder;
import com.trinary.test.hateoas.UserResource;
import com.trinary.test.hateoas.UserResourceAssembler;
import com.trinary.test.persistence.entity.Blog;
import com.trinary.test.persistence.entity.Comment;
import com.trinary.test.persistence.entity.User;
import com.trinary.test.service.BlogService;

@Controller
@RequestMapping("/blog")
public class BlogControllerV2 {
	@Autowired
	protected BlogService blogService;
	@Autowired
	protected BlogResourceAssembler blogAssembler;
	@Autowired
	protected UserResourceAssembler userAssembler;
	@Autowired
	protected CommentResourceAssembler commentAssembler;

	@Autowired
	protected ResourceListBuilder resourceListBuilder;

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public BlogResource create(@RequestBody Blog blog) {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		blog.setUser(user);
		return blogAssembler.toResource(blogService.save(blog));
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/{blogId}/comment", method = RequestMethod.POST)
	@ResponseBody
	public CommentResource commentOn(@PathVariable("blogId") Long blogId,
			@RequestBody Comment comment) {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		comment.setOwner(user);

		return commentAssembler.toResource(blogService.addComment(blogId,
				comment));
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/{blogId}", method = RequestMethod.DELETE)
	@ResponseBody
	public BlogResource delete(@PathVariable("blogId") long blogId) {
		Blog blog = blogService.get(blogId);
		blogService.delete(blog);
		return blogAssembler.toResource(blog);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public ResourceList<BlogResource> getAll(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "order", required = false) String orderBy) {
		Integer count = blogService.getCount();
		return resourceListBuilder
				.<BlogResource> createResourceList(blogAssembler
						.toResources(blogService.getAll(page, pageSize,
								OrderPair.generateAll(orderBy))), count, page,
						pageSize);
	}

	@RequestMapping(value = "/{blogId}", method = RequestMethod.GET)
	@ResponseBody
	public BlogResource get(@PathVariable("blogId") Long blogId) {
		System.out.println("IN GET");
		BlogResource blog = blogAssembler.toResource(blogService.get(blogId));
		return blog;
	}

	@RequestMapping(value = "/{blogId}/author", method = RequestMethod.GET)
	@ResponseBody
	public UserResource getAuthor(@PathVariable("blogId") Long blogId) {
		System.out.println("IN GET");
		UserResource user = userAssembler.toResource(blogService
				.getUser(blogId));
		return user;
	}

	@RequestMapping(value = "/{blogId}/comments", method = RequestMethod.GET)
	@ResponseBody
	public ResourceList<CommentResource> getComments(
			@PathVariable("blogId") Long blogId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "order", required = false) String orderBy) {
		System.out.println("IN GET COMMENTS");

		try {
			Integer count = blogService.getCommentsCount(blogId);
			return resourceListBuilder.<CommentResource> createResourceList(
					commentAssembler.toResources(blogService.getComments(
							blogId, OrderPair.generateAll(orderBy), page,
							pageSize)), count, page, pageSize);
		} catch (Exception e) {
			return null;
		}
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	@ResponseBody
	public BlogResource createTest() {
		System.out.println("IN CREATE");
		BlogResource blog = blogAssembler.toResource(blogService
				.createTestBlog());
		return blog;
	}

	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseBody
	public Map<String, String> accessDenied(HttpServletResponse response,
			AccessDeniedException e) {
		response.setStatus(405);

		Map<String, String> errorMap = new HashMap<String, String>();
		errorMap.put("error", e.getClass().toString());
		errorMap.put("message", "User is not authorized to use this service.");
		return errorMap;
	}

	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AuthenticationException.class)
	@ResponseBody
	public Map<String, String> authError(HttpServletResponse response,
			AuthenticationException e) {
		response.setStatus(405);

		Map<String, String> errorMap = new HashMap<String, String>();
		errorMap.put("error", e.getClass().toString());
		errorMap.put("message", "User is unauthenticated.");
		return errorMap;
	}
}