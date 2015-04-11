package com.trinary.test.hateoas;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class ResourceListBuilderImpl implements ResourceListBuilder {
	/* (non-Javadoc)
	 * @see com.trinary.test.hateoas.ResourceListBuilder#createResourceList(java.util.List, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public <T extends ResourceSupport> ResourceList<T> createResourceList(List<T> resources, Integer count, Integer page, Integer pageSize) {
		ResourceList<T> resourceList = new ResourceList<T>();
		resourceList.setResources(resources);
		if (page != null && pageSize != null) {
			resourceList.setPage(page);
			resourceList.setPages((int)Math.ceil((float)count/(float)pageSize));
		}
		
		return resourceList;
	}
}