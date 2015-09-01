package com.trinary.test.service;

import java.util.List;

import com.trinary.persistence.OrderPair;
import com.trinary.test.persistence.entity.Blog;
import com.trinary.test.persistence.entity.Comment;
import com.trinary.test.persistence.entity.User;

public interface UserService {
	User get(long id);
	User getByApiKey(String apiKey) throws Exception;
	List<User> getAll(Integer page, Integer pageSize, List<OrderPair> orderBy);
	
	User save(User user);
	Integer getBlogsCount(long id);
	List<Blog> getBlogs(long id);
	List<Comment> getComments(long userId);
	List<Comment> getComments(long userId, Integer page, Integer pageSize) throws Exception;
	User getByUsername(String username) throws Exception;
	List<Comment> getComments(long userId, List<OrderPair> orderBy,
			Integer page, Integer pageSize) throws Exception;
}