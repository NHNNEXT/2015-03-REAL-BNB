package com.babydear.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Post {
	public enum State {
		NORMAL,
		DELETE
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long pId;
	
	@ManyToOne
	private Family fid;
	
	private String title;
	private String content;
	private List<PostImage> images;	
	
	private User user;
	private Date write;
	private Date modify;
	private State state;
}
