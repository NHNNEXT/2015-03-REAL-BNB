package com.babydear.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Family {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long fId;
	
//	List<User> users;
//	List<Baby> babys;
//	List<Post> posts;
//	List<Album> albums;
	
}
