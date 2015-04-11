package com.trinary.test.service;

import java.util.List;

import com.trinary.persistence.OrderPair;
import com.trinary.test.persistence.entity.Blog;
import com.trinary.test.persistence.entity.Comment;
import com.trinary.test.persistence.entity.User;

public interface CommentService {
	Comment get(long id);
	List<Comment> getAll(Integer page, Integer pageSize, List<OrderPair> orderBy);
	User getAuthor(long id);
	Blog getBlog(long commentId);
	Comment save(Comment comment);
}