package com.babydear.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CardImage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long iId;

	@ManyToOne
	private Card cId;
	
	private String originUrl;
	private String recentUrl;
	private Date CreateDate;
}
