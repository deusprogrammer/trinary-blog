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
import com.trinary.test.service.CommentService;

@Controller
@RequestMapping(value="/comment")
public class CommentControllerV2 {
	@Autowired CommentService commentService;
	@Autowired protected BlogResourceAssembler blogAssembler;
	@Autowired protected UserResourceAssembler userAssembler;
	@Autowired protected CommentResourceAssembler commentAssembler;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	@ResponseBody
	public List<CommentResource> getAll(
			@RequestParam(value = "page", required=false) Integer page, 
			@RequestParam(value = "pageSize", required=false) Integer pageSize,
			@RequestParam(value = "order", required=false) String orderBy) {
		return commentAssembler.toResources(commentService.getAll(page, pageSize, OrderPair.generateAll(orderBy)));
	}
	
	@RequestMapping(value="/{commentId}", method=RequestMethod.GET)
	@ResponseBody
	public CommentResource getComment(@PathVariable("commentId") Long commentId) {
		return commentAssembler.toResource(commentService.get(commentId));
	}
	
	@RequestMapping(value="/{commentId}/blog", method=RequestMethod.GET)
	@ResponseBody
	public BlogResource getBlog(@PathVariable("commentId") Long commentId) {
		return blogAssembler.toResource(commentService.getBlog(commentId));
	}
	
	@RequestMapping(value="/{commentId}/author", method=RequestMethod.GET)
	@ResponseBody
	public UserResource getAuthor(@PathVariable("commentId") Long commentId) {
		return userAssembler.toResource(commentService.getAuthor(commentId));
	}
}