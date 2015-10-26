package com.babydear.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class BabySticker {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long sId;
	
	@ManyToOne
	private Baby bid;
	
	private String originUrl;
	private String recentUrl;
	
}
