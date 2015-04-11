package com.trinary.test.persistence.dao;

import com.trinary.persistence.BaseDAO;
import com.trinary.persistence.Paginate;
import com.trinary.test.persistence.entity.Blog;

public interface BlogDAO extends BaseDAO<Blog>, Paginate<Blog> {}