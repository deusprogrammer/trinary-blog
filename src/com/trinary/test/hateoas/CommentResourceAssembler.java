package com.trinary.test.hateoas;

import java.util.List;

import com.trinary.test.persistence.entity.Comment;

public interface CommentResourceAssembler {

	/* (non-Javadoc)
	 * @see com.trinary.test.hateoas.CommentResourceAssembler#toResource(com.trinary.test.persistence.entity.Comment)
	 */
	public abstract CommentResource toResource(Comment comment);

	/* (non-Javadoc)
	 * @see org.springframework.hateoas.mvc.ResourceAssemblerSupport#toResources(java.lang.Iterable)
	 */
	public abstract List<CommentResource> toResources(
			Iterable<? extends Comment> comments);

}