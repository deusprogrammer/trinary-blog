package com.trinary.test.controller.v2;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/")
public class BaseControllerV2 {
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	@ResponseBody
	public ResourceSupport getEntryPoints() {
		ResourceSupport resource = new ResourceSupport();
		resource.add(linkTo(methodOn(BaseControllerV2.class).getEntryPoints()).withSelfRel());
		resource.add(linkTo(methodOn(BlogControllerV2.class).getAll(null, null, null)).withRel("blogs"));
		resource.add(linkTo(methodOn(CommentControllerV2.class).getAll(null, null, null)).withRel("comments"));
		resource.add(linkTo(methodOn(UserControllerV2.class).getAll(null, null, null)).withRel("users"));
		return resource;
	}
}