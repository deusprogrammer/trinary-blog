package com.trinary.test.controller.v1;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/")
public class BaseController {
	
	/* (non-Javadoc)
	 * @see com.trinary.test.controller.v1.IBaseController#getEntryPoints()
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	@ResponseBody
	public ResourceSupport getEntryPoints() {
		ResourceSupport resource = new ResourceSupport();
		resource.add(linkTo(methodOn(BaseController.class).getEntryPoints()).withSelfRel());
		resource.add(linkTo(methodOn(BlogController.class).getAll(null, null, null)).withRel("blogs"));
		resource.add(linkTo(methodOn(CommentController.class).getAll(null, null, null)).withRel("comments"));
		resource.add(linkTo(methodOn(UserController.class).getAll(null, null, null)).withRel("users"));
		return resource;
	}
}