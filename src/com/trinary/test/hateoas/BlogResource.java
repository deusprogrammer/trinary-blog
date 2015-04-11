package com.trinary.test.hateoas;

import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

public class BlogResource extends ResourceSupport {
	protected long blogId;
	
	protected Date created;
	protected Date updated;
	protected String title;
	protected String text;
	
	/**
	 * @return the id
	 */
	public long getBlogId() {
		return blogId;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setBlogId(long id) {
		this.blogId = id;
	}
	
	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}
	
	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}
	
	/**
	 * @return the updated
	 */
	public Date getUpdated() {
		return updated;
	}
	
	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
}
