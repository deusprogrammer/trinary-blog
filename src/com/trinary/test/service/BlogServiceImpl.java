package com.trinary.test.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import com.trinary.persistence.OrderPair;
import com.trinary.test.persistence.dao.BlogDAO;
import com.trinary.test.persistence.dao.CommentDAO;
import com.trinary.test.persistence.entity.Blog;
import com.trinary.test.persistence.entity.Comment;
import com.trinary.test.persistence.entity.User;

@Transactional
public class BlogServiceImpl implements BlogService {
	@Autowired protected BlogDAO blogDAO;
	@Autowired protected CommentDAO commentDAO;
	@Autowired protected UserService userService;
	@Autowired protected CommentService commentService;
	
	protected static final String LOREM_IPSUM = 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis ullamcorper sollicitudin nisl, vel efficitur turpis molestie in. Donec pretium diam ipsum, vel porta dolor condimentum ut. Ut eu ex scelerisque, facilisis ante id, varius augue. Phasellus non egestas dui. Nullam libero diam, hendrerit ut tellus at, cursus accumsan elit. Nullam dignissim imperdiet tempus. Etiam dictum lacus et ligula lacinia, non porttitor massa efficitur. Morbi nec volutpat mauris. Morbi sem nunc, ultrices et efficitur in, rhoncus nec lacus. Sed luctus erat tincidunt auctor pulvinar. Nulla consectetur risus sit amet semper molestie. Nulla porta lectus id lobortis placerat.\n\n" +
		    "Aliquam erat volutpat. Donec vehicula imperdiet feugiat. Duis eleifend urna auctor, vulputate lectus in, iaculis dolor. In ac mi felis. In in tempus est. Nullam rhoncus, lacus sit amet mollis consequat, eros tortor sollicitudin augue, at venenatis augue nunc sed nisl. Donec fringilla, est ac ultricies accumsan, justo lectus luctus dolor, lobortis hendrerit tortor metus vel tellus. In sed semper nisl, vitae cursus ante. Vivamus tincidunt arcu ac ex facilisis dictum sed quis nisi. Aenean faucibus, nulla at pharetra efficitur, dui massa eleifend arcu, eu aliquam leo magna nec ante. Donec eget volutpat arcu, eu dapibus ante. Sed id consectetur arcu. Donec posuere eros iaculis turpis posuere maximus. Vestibulum ac hendrerit lectus, at rutrum mauris. Mauris arcu leo, venenatis vel sem sed, sodales accumsan purus.\n\n" +
		    "Donec purus odio, molestie vel lorem sit amet, pharetra dapibus nisl. Sed eleifend quis turpis et tincidunt. Ut purus nisl, efficitur ut nulla eget, convallis finibus augue. Nunc ac lacinia nulla, eu aliquam lorem. Donec sit amet purus pharetra, accumsan mi at, luctus neque. Curabitur mattis neque ac ante vulputate vestibulum. Donec ultricies risus eget tortor facilisis, vitae tincidunt lacus euismod. Curabitur in magna non augue convallis ornare at et urna. Vestibulum commodo tempor nibh aliquet fringilla. Duis ac sem feugiat, ornare magna ac, laoreet massa. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla tincidunt cursus ipsum, posuere ullamcorper sem vestibulum suscipit. Donec lobortis sollicitudin elit, condimentum volutpat neque iaculis in. Aenean tortor dui, commodo eget nulla nec, sagittis cursus nulla. Proin nec arcu eu massa consectetur lacinia. Duis convallis, nunc eu maximus vulputate, ante turpis malesuada ligula, ullamcorper ultricies velit nunc non est.\n\n" +
		    "Vivamus at est ullamcorper, tristique leo sit amet, bibendum massa. Phasellus consectetur nisl vitae mi aliquet, eu lobortis sem aliquet. Quisque finibus dolor et interdum gravida. Sed semper quam erat, sed aliquet orci egestas in. In ullamcorper, quam non molestie fermentum, est magna finibus ante, id auctor velit odio nec diam. Praesent tempor, sem eu finibus lobortis, nisi libero euismod quam, in pulvinar nibh lacus in nulla. Ut ullamcorper lorem sed magna posuere, vitae faucibus sapien porttitor. Praesent in suscipit est. In lacinia sagittis orci, quis fringilla diam lobortis id. Proin sodales, sem nec venenatis auctor, arcu sem pretium eros, id placerat odio nisl non elit. Aliquam cursus libero sit amet lectus venenatis, sed pharetra nisi convallis. Nulla magna elit, pharetra id accumsan ac, fermentum quis neque. Suspendisse ullamcorper bibendum metus nec molestie.\n\n" +
		    "Sed scelerisque nisi enim. Morbi sed condimentum lorem, eleifend ullamcorper nulla. Proin in finibus elit. Cras vulputate luctus purus nec luctus. Morbi sodales sit amet nulla vel cursus. Aenean ac mi massa. Nunc at vulputate quam. Nulla purus sapien, venenatis in posuere id, eleifend convallis magna.";
	
	/* (non-Javadoc)
	 * @see com.trinary.test.service.BlogService#createTestBlog()
	 */
	@Override
	public Blog createTestBlog() {
		Date now = new Date();
		User user1 = new User();
		user1.setUsername("deusprogrammer");
		user1.setCredentials("PinkPantsu1!");
		user1.setEmailAddress("deusprogrammer@gmail.com");
		
		User user2 = new User();
		user2.setUsername("diva_miku");
		user2.setCredentials("Fre5hShimap@n");
		user2.setEmailAddress("deusprogrammer@yahoo.com");
		
		List<Blog> blogs = new ArrayList<Blog>();
		
		Blog blog = new Blog();
		blog.setCreated(now);
		blog.setUpdated(now);
		blog.setTitle("Test Blog 1");
		blog.setText(LOREM_IPSUM);
		
		now = new Date();
		List<Comment> comments = new ArrayList<Comment>();
		Comment comment = new Comment();
		comment.setCreated(now);
		comment.setUpdated(now);
		comment.setText("TEST COMMENT 1");
		comment.setBlog(blog);
		comment.setOwner(user2);
		comments.add(comment);
		
		user2.getComments().add(comment);
		
		now = new Date();
		comment = new Comment();
		comment.setCreated(now);
		comment.setUpdated(now);
		comment.setText("TEST COMMENT 2");
		comment.setBlog(blog);
		comment.setOwner(user2);
		comments.add(comment);
		
		user2.getComments().add(comment);
		
		blog.setComments(comments);
		blog.setUser(user1);
		blogs.add(blog);
		
		blog = new Blog();
		blog.setCreated(now);
		blog.setUpdated(now);
		blog.setTitle("Test Blog 2");
		blog.setText(LOREM_IPSUM);
		
		now = new Date();
		comments = new ArrayList<Comment>();
		comment = new Comment();
		comment.setCreated(now);
		comment.setUpdated(now);
		comment.setText("TEST COMMENT 3");
		comment.setBlog(blog);
		comment.setOwner(user2);
		comments.add(comment);
		
		user2.getComments().add(comment);
		
		now = new Date();
		comment = new Comment();
		comment.setCreated(now);
		comment.setUpdated(now);
		comment.setText("TEST COMMENT 4");
		comment.setBlog(blog);
		comment.setOwner(user2);
		comments.add(comment);
		
		user2.getComments().add(comment);
		
		blog.setComments(comments);
		blog.setUser(user1);
		blogs.add(blog);
		
		user1.setBlogs(blogs);
		userService.save(user1);
		userService.save(user2);
		
		return blog;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.trinary.test.service.BlogService#getCount()
	 */
	@Override
	public Integer getCount() {
		return blogDAO.getAll().size();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.trinary.test.service.BlogService#getAll(java.lang.Integer, java.lang.Integer, java.util.List)
	 */
	@Override
	public List<Blog> getAll(Integer page, Integer pageSize, List<OrderPair> orderBy) {
		return blogDAO.getPaginatedAndOrdered(page, pageSize, orderBy);
	}

	/* (non-Javadoc)
	 * @see com.trinary.test.service.BlogService#getResource(long)
	 */
	@Override
	public Blog get(long id) {
		return blogDAO.get(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.trinary.test.service.BlogService#getUser(long)
	 */
	@Override
	public User getUser(long id) {
		Blog blog = blogDAO.get(id);
		return blog.getUser();
	}

	/* (non-Javadoc)
	 * @see com.trinary.test.service.BlogService#save(com.trinary.test.persistence.entity.Blog)
	 */
	@Override
	public Blog save(Blog blog) {
		blogDAO.save(blog);
		return blog;
	}

	@Override
	public List<Comment> getComments(long id, Integer page,
			Integer pageSize) throws Exception {
		return getComments(id, Collections.<OrderPair> emptyList(), page, pageSize);
	}
	
	@Override
	public List<Comment> getComments(long id, List<OrderPair> orderBy, Integer page,
			Integer pageSize) throws Exception {
		Integer offset = null;
		if (page != null && pageSize != null) {
			offset = pageSize * (page - 1);
		}
		
		List<Criterion> criterion = new ArrayList<Criterion>();
		criterion.add(Restrictions.eq("blog.id", id));
		List<Comment> list = new ArrayList<Comment>(commentDAO.findAll(criterion, orderBy, offset, pageSize));
		return list;
	}
	
	public Integer getCommentsCount(long id) throws Exception {		
		List<Criterion> criterion = new ArrayList<Criterion>();
		criterion.add(Restrictions.eq("blog.id", id));
		List<Comment> list = new ArrayList<Comment>(commentDAO.findAll(criterion, null, null, null));
		return list.size();
	}

	/* (non-Javadoc)
	 * @see com.trinary.test.service.BlogService#delete(com.trinary.test.persistence.entity.Blog)
	 */
	@Override
	@PreAuthorize("#blog.user.id == principal.id")
	public void delete(Blog blog) {
		//blogDAO.delete(blog);
		System.out.println("DELETING: " + blog.getId());
	}

	@Override
	public Comment addComment(long id, Comment comment) {
		Blog blog = get(id);
		if (blog == null) {
			return null;
		}
		
		blog.getComments().add(comment);
		save(blog);
		
		comment.setBlog(blog);
		commentService.save(comment);
		
		return comment;
	}
}