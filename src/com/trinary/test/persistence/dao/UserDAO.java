package com.trinary.test.persistence.dao;

import com.trinary.persistence.BaseDAO;
import com.trinary.persistence.Paginate;
import com.trinary.test.persistence.entity.User;

public interface UserDAO extends BaseDAO<User>, Paginate<User> {}