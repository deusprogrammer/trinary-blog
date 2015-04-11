package com.trinary.test.persistence.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.trinary.persistence.GenericDAO;
import com.trinary.test.persistence.entity.Comment;

public class CommentDAOImpl extends GenericDAO<Comment> implements CommentDAO {
	@Autowired SessionFactory sessionFactory;

	public CommentDAOImpl() {
		this.type = Comment.class;
	}
	
	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}