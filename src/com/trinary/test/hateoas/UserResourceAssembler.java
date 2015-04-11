package com.trinary.test.hateoas;

import java.util.List;

import com.trinary.test.persistence.entity.User;

public interface UserResourceAssembler {
	public abstract UserResource toResource(User user);
	public abstract List<UserResource> toResources(Iterable<? extends User> user);
}