package com.trinary.test.hateoas.v1;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.trinary.test.controller.v1.CommentController;
import com.trinary.test.hateoas.CommentResource;
import com.trinary.test.hateoas.CommentResourceAssembler;
import com.trinary.test.persistence.entity.Comment;

public class CommentResourceAssemblerV1 extends ResourceAssemblerSupport<Comment, CommentResource> implements CommentResourceAssembler {

	public CommentResourceAssemblerV1() {
		super(CommentController.class, CommentResource.class);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.trinary.test.hateoas.CommentResourceAssembler#toResource(com.trinary.test.persistence.entity.Comment)
	 */
	@Override
	public CommentResource toResource(Comment comment) {
		if (comment == null) {
			return null;
		}
		
		CommentResource resource = this.createResourceWithId(comment.getId(), comment);
		resource.add(linkTo(methodOn(CommentController.class).getBlog(comment.getBlog().getId())).withRel("blog"));
		resource.add(linkTo(methodOn(CommentController.class).getAuthor(comment.getId())).withRel("author"));
		
		return resource;
	}

	/* (non-Javadoc)
	 * @see com.trinary.test.hateoas.CommentResourceAssembler#toResources(java.lang.Iterable)
	 */
	@Override
	public List<CommentResource> toResources(Iterable<? extends Comment> comments) {
		// TODO Auto-generated method stub
		return super.toResources(comments);
	}

	/* (non-Javadoc)
	 * @see org.springframework.hateoas.mvc.ResourceAssemblerSupport#instantiateResource(java.lang.Object)
	 */
	@Override
	protected CommentResource instantiateResource(Comment comment) {
		CommentResource resource = new CommentResource();
		resource.setCommentId(comment.getId());
		resource.setCreated(comment.getCreated());
		resource.setUpdated(comment.getUpdated());
		resource.setText(comment.getText());
		return resource;
	}
}
