package com.trinary.test.hateoas.v2;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.trinary.test.controller.v2.BlogControllerV2;
import com.trinary.test.hateoas.BlogResource;
import com.trinary.test.hateoas.BlogResourceAssembler;
import com.trinary.test.persistence.entity.Blog;

public class BlogResourceAssemblerV2 extends ResourceAssemblerSupport<Blog, BlogResource> implements BlogResourceAssembler {
	public BlogResourceAssemblerV2() {
		super(BlogControllerV2.class, BlogResource.class);
	}
	
	public BlogResourceAssemblerV2(Class<?> controllerClass,
			Class<BlogResource> resourceType) {
		super(controllerClass, resourceType);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.trinary.test.hateoas.BlogResourceAssembler#toResource(com.trinary.test.persistence.entity.Blog)
	 */
	@Override
	public BlogResource toResource(Blog blog) {
		if (blog == null) {
			return null;
		}
		
		BlogResource resource = createResourceWithId(blog.getId(), blog);
		resource.add(linkTo(methodOn(BlogControllerV2.class).getAuthor(blog.getId())).withRel("author"));
		resource.add(linkTo(methodOn(BlogControllerV2.class).getComments(blog.getId(), null, null, null)).withRel("comments"));
		resource.add(linkTo(methodOn(BlogControllerV2.class).commentOn(blog.getId(), null)).withRel("_comment"));
		return resource;
	}

	/* (non-Javadoc)
	 * @see com.trinary.test.hateoas.BlogResourceAssembler#toResources(java.lang.Iterable)
	 */
	@Override
	public List<BlogResource> toResources(Iterable<? extends Blog> blogs) {
		return super.toResources(blogs);
	}

	/* (non-Javadoc)
	 * @see org.springframework.hateoas.mvc.ResourceAssemblerSupport#instantiateResource(java.lang.Object)
	 */
	@Override
	protected BlogResource instantiateResource(Blog blog) {
		// TODO Auto-generated method stub
		BlogResource resource = new BlogResource();
		resource.setCreated(blog.getCreated());
		resource.setUpdated(blog.getUpdated());
		resource.setTitle(blog.getTitle());
		resource.setText(blog.getText());
		resource.setBlogId(blog.getId());
		
		return resource;
	}
}
