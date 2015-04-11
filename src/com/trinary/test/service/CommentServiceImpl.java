package com.trinary.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.trinary.persistence.OrderPair;
import com.trinary.test.persistence.dao.CommentDAO;
import com.trinary.test.persistence.entity.Blog;
import com.trinary.test.persistence.entity.Comment;
import com.trinary.test.persistence.entity.User;

@Transactional
public class CommentServiceImpl implements CommentService {
	@Autowired CommentDAO commentDAO;
	
	public CommentServiceImpl(){}
	
	/* (non-Javadoc)
	 * @see com.trinary.test.service.CommentService#get(long)
	 */
	@Override
	public Comment get(long id) {
		return commentDAO.get(id);
	}
	
	/* (non-Javadoc)
	 * @see com.trinary.test.service.CommentService#getAll(java.lang.Integer, java.lang.Integer, java.util.List)
	 */
	@Override
	public List<Comment> getAll(Integer page, Integer pageSize,
			List<OrderPair> orderBy) {
		return commentDAO.getPaginatedAndOrdered(page, pageSize, orderBy);
	}

	@Override
	public User getAuthor(long id) {
		return commentDAO.get(id).getOwner();
	}
	
	@Override
	public Blog getBlog(long id) {
		return commentDAO.get(id).getBlog();
	}
	
	/* (non-Javadoc)
	 * @see com.trinary.test.service.CommentService#save(com.trinary.test.persistence.entity.Comment)
	 */
	@Override
	public Comment save(Comment comment) {
		commentDAO.save(comment);
		return comment;
	}
}
