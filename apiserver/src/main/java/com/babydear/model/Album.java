package com.babydear.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Album {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long aId;
	
	@ManyToOne
	private Family fid;
	
	private String name;
	
	private List<Post> posts;
	
}
