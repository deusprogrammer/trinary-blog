package com.trinary.test.hateoas.v2;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.trinary.test.controller.v2.UserControllerV2;
import com.trinary.test.hateoas.UserResource;
import com.trinary.test.hateoas.UserResourceAssembler;
import com.trinary.test.persistence.entity.User;
import com.trinary.test.util.GravatarUtil;

public class UserResourceAssemblerV2 extends ResourceAssemblerSupport<User, UserResource> implements UserResourceAssembler {

	public UserResourceAssemblerV2() {
		super(UserControllerV2.class, UserResource.class);
	}

	/* (non-Javadoc)
	 * @see com.trinary.test.hateoas.UserResourceAssembler#toResource(com.trinary.test.persistence.entity.User)
	 */
	@Override
	public UserResource toResource(User user) {
		if (user == null) {
			return null;
		}
		
		UserResource resource = createResourceWithId(user.getId(), user);
		resource.add(new Link(GravatarUtil.getGravatarUrl(user.getEmailAddress())).withRel("avatar"));
		resource.add(linkTo(methodOn(UserControllerV2.class).getBlogs(user.getId())).withRel("blogs"));
		resource.add(linkTo(methodOn(UserControllerV2.class).getComments(user.getId(), null, null)).withRel("comments"));
		return resource;
	}

	/* (non-Javadoc)
	 * @see org.springframework.hateoas.mvc.ResourceAssemblerSupport#toResources(java.lang.Iterable)
	 */
	@Override
	public List<UserResource> toResources(Iterable<? extends User> user) {
		// TODO Auto-generated method stub
		return super.toResources(user);
	}

	/* (non-Javadoc)
	 * @see org.springframework.hateoas.mvc.ResourceAssemblerSupport#instantiateResource(java.lang.Object)
	 */
	@Override
	protected UserResource instantiateResource(User user) {
		UserResource resource = new UserResource();
		resource.setUsername(user.getUsername());
		resource.setUserId(user.getId());
		
		return resource;
	}
}
