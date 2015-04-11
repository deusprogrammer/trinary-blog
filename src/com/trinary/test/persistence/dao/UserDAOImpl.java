package com.trinary.test.persistence.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.trinary.persistence.GenericDAO;
import com.trinary.test.persistence.entity.User;

public class UserDAOImpl extends GenericDAO<User> implements UserDAO {
	@Autowired SessionFactory sessionFactory;
	
	public UserDAOImpl() {
		this.type = User.class;
	}
	
	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
