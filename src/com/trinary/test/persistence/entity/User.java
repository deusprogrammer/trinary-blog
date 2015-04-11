package com.trinary.test.persistence.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="user")
public class User implements UserDetails {
	private static final long serialVersionUID = 1L;

	public static final User ANONYMOUS = new User();
	static {
		ANONYMOUS.id = -1;
		ANONYMOUS.username = "anonymous";
		ANONYMOUS.credentials   = "";
		ANONYMOUS.blogs = Collections.emptyList();
		ANONYMOUS.comments = Collections.emptyList();
	}
	
	@Id
	@GeneratedValue
	protected long id;
	
	@Column(name="username")
	protected String username;
	
	@Column(name="emailAddress")
	protected String emailAddress;
	
	@Column(name="credentials")
	protected String credentials;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="user")
	protected List<Blog> blogs = new ArrayList<Blog>();
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="owner")
	protected List<Comment> comments = new ArrayList<Comment>();

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the apiKey
	 */
	public String getCredentials() {
		return credentials;
	}

	/**
	 * @param apiKey the apiKey to set
	 */
	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}

	/**
	 * @return the blogs
	 */
	public List<Blog> getBlogs() {
		return blogs;
	}

	/**
	 * @param blogs the blogs to set
	 */
	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}

	/**
	 * @return the comments
	 */
	public List<Comment> getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	// Spring Security Functions

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
		return authorities;
	}

	@Override
	public String getPassword() {
		return credentials;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}