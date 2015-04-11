package com.trinary.test.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.trinary.persistence.OrderPair;
import com.trinary.test.persistence.dao.CommentDAO;
import com.trinary.test.persistence.dao.UserDAO;
import com.trinary.test.persistence.entity.Blog;
import com.trinary.test.persistence.entity.Comment;
import com.trinary.test.persistence.entity.User;

@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
	@Autowired protected UserDAO userDAO;
	@Autowired protected CommentDAO commentDAO;
	
	public UserServiceImpl() {};
	
	/* (non-Javadoc)
	 * @see com.trinary.test.service.UserService#get(long)
	 */
	@Override
	public User get(long id) {
		return userDAO.get(id);
	}
	
	/* (non-Javadoc)
	 * @see com.trinary.test.service.UserService#getAll(java.lang.Integer, java.lang.Integer, java.util.List)
	 */
	@Override
	public List<User> getAll(Integer page, Integer pageSize,
			List<OrderPair> orderBy) {
		return userDAO.getPaginatedAndOrdered(page, pageSize, orderBy);
	}

	/* (non-Javadoc)
	 * @see com.trinary.test.service.UserService#save(com.trinary.test.persistence.entity.User)
	 */
	@Override
	public void save(User user) {
		userDAO.save(user);
	}
	
	public Integer getBlogsCount(long id) {
		User user = get(id);
		return user.getBlogs().size();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.trinary.test.service.UserService#getBlogs(long)
	 */
	@Override
	public List<Blog> getBlogs(long id) {
		User user = get(id);
		return new ArrayList<Blog>(user.getBlogs());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.trinary.test.service.UserService#getComments(long)
	 */
	@Override
	public List<Comment> getComments(long id) {
		return new ArrayList<Comment>(userDAO.get(id).getComments());
	}

	/*
	 * (non-Javadoc)
	 * @see com.trinary.test.service.UserService#getComments(long, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Comment> getComments(long userId, Integer page,
			Integer pageSize) throws Exception {
		return getComments(userId, Collections.<OrderPair> emptyList(), page, pageSize);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.trinary.test.service.UserService#getComments(long, java.util.List, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Comment> getComments(long userId, List<OrderPair> orderBy, Integer page,
			Integer pageSize) throws Exception {
		Integer offset = pageSize * (page - 1);
		List<Criterion> criterion = new ArrayList<Criterion>();
		criterion.add(Restrictions.eq("owner.id", userId));
		return commentDAO.findAll(criterion, orderBy, offset, pageSize);
	}

	/*
	 * (non-Javadoc)
	 * @see com.trinary.test.service.UserService#getByApiKey(java.lang.String)
	 */
	@Override
	public User getByApiKey(String apiKey) throws Exception {
		List<Criterion> criteria = new ArrayList<Criterion>();
		criteria.add(Restrictions.eq("credentials", apiKey));
		return userDAO.findOne(criteria, null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.trinary.test.service.UserService#getByUsername(java.lang.String)
	 */
	@Override
	public User getByUsername(String username) throws Exception {
		List<Criterion> criteria = new ArrayList<Criterion>();
		criteria.add(Restrictions.eq("username", username));
		return userDAO.findOne(criteria, null);
	}
	
	// Spring Security functions
	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		try {
			return getByUsername(username);
		} catch (Exception e) {
			throw new UsernameNotFoundException("Unable to load user from username " + username);
		}
	}
}
