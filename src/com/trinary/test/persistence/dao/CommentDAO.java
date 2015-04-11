package com.trinary.test.persistence.dao;

import com.trinary.persistence.BaseDAO;
import com.trinary.persistence.Paginate;
import com.trinary.test.persistence.entity.Comment;

public interface CommentDAO extends BaseDAO<Comment>, Paginate<Comment> {}