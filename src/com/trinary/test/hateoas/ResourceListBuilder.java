package com.trinary.test.hateoas;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public interface ResourceListBuilder {

	public abstract <T extends ResourceSupport> ResourceList<T> createResourceList(
			List<T> resources, Integer count, Integer page,
			Integer pageSize);

}