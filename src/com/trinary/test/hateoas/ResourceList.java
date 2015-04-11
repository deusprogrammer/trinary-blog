package com.trinary.test.hateoas;

import java.util.List;

public class ResourceList<T> {
	protected Integer page;
	protected Integer pages;
	protected List<T> resources;

	/* (non-Javadoc)
	 * @see com.trinary.test.hateoas.ResourceList#getPage()
	 */
	public Integer getPage() {
		return page;
	}

	/* (non-Javadoc)
	 * @see com.trinary.test.hateoas.ResourceList#getPages()
	 */
	public Integer getPages() {
		return pages;
	}

	/* (non-Javadoc)
	 * @see com.trinary.test.hateoas.ResourceList#getResources()
	 */
	public List<T> getResources() {
		return resources;
	}

	/* (non-Javadoc)
	 * @see com.trinary.test.hateoas.ResourceList#setPage(java.lang.Integer)
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/* (non-Javadoc)
	 * @see com.trinary.test.hateoas.ResourceList#setPages(java.lang.Integer)
	 */
	public void setPages(Integer pages) {
		this.pages = pages;
	}

	/* (non-Javadoc)
	 * @see com.trinary.test.hateoas.ResourceList#setResources(java.util.List)
	 */
	public void setResources(List<T> resources) {
		this.resources = resources;
	}
}