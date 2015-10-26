package com.babydear.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class PostImage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long iId;
	
	@ManyToOne
	private Post pid;

}
