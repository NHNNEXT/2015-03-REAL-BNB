package com.babydear.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

public class Hash {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long hId;
	
	@OneToOne
	private Baby baby;
	
	@OneToOne
	private Post post;
	
}
