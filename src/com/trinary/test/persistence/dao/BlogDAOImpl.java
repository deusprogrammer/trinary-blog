package com.trinary.test.persistence.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.trinary.persistence.GenericDAO;
import com.trinary.test.persistence.entity.Blog;

public class BlogDAOImpl extends GenericDAO<Blog> implements BlogDAO {
	@Autowired SessionFactory sessionFactory;
	
	public BlogDAOImpl() {
		this.type = Blog.class;
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}