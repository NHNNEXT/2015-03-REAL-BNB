package com.babydear.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.babydear.dto.CardDTO;

@Entity
public class Card {
	public Card(){}
	public Card(CardDTO cardDTO) {
		content = cardDTO.getContent();
		
//		modified = cardDTO.ge
	}
	public enum State {
		NORMAL, DELETE
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cId;

	@ManyToOne
	private Family fId;

	@OneToMany
	private List<CardImage> images;

	@ManyToOne
	private User writer;

	private String content;
	private Date createDate;
	private Date updateDate;
	private Date modifiedDate;
	private State state;
	
}
