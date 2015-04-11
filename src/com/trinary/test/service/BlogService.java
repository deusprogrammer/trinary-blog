package com.trinary.test.service;

import java.util.List;

import com.trinary.persistence.OrderPair;
import com.trinary.test.persistence.entity.Blog;
import com.trinary.test.persistence.entity.Comment;
import com.trinary.test.persistence.entity.User;

public interface BlogService {
	Blog createTestBlog();
	Blog get(long id);
	List<Comment> getComments(long id, Integer page, Integer pageSize) throws Exception;
	User getUser(long id);
	Blog save(Blog blog);
	void delete(Blog blog);
	Comment addComment(long id, Comment comment);
	Integer getCommentsCount(long id) throws Exception;
	List<Comment> getComments(long id, List<OrderPair> orderPair, Integer page,
			Integer pageSize) throws Exception;
	List<Blog> getAll(Integer page, Integer pageSize, List<OrderPair> orderBy);
	Integer getCount();
}