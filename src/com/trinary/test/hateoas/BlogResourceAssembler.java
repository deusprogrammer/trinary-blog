package com.trinary.test.hateoas;

import java.util.List;

import com.trinary.test.persistence.entity.Blog;

public interface BlogResourceAssembler {

	/* (non-Javadoc)
	 * @see com.trinary.test.hateoas.BlogResourceAssembler#toResource(com.trinary.test.persistence.entity.Blog)
	 */
	public abstract BlogResource toResource(Blog blog);

	/* (non-Javadoc)
	 * @see org.springframework.hateoas.mvc.ResourceAssemblerSupport#toResources(java.lang.Iterable)
	 */
	public abstract List<BlogResource> toResources(
			Iterable<? extends Blog> blogs);

}