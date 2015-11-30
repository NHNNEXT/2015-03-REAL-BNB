package com.babydear.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.babydear.dto.CardDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cId;

	@ManyToOne
	private Family fId;

	@ManyToOne
	private User writer;
	
	@OneToMany Set<Baby> babies;

	private String image;
	private String content;
	private String modifiedDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	private Boolean deleted;
	
	public Card() {
	}
	public Card(Family fId, User writer, Set<Baby> babies, String image, String content, String modifiedDate) {
		super();
		this.fId = fId;
		this.writer = writer;
		this.babies = babies;
		this.image = image;
		this.content = content;
		this.modifiedDate = modifiedDate;
	}

	public void create(CardDTO cardDTO) {
		content = cardDTO.getContent();
		modifiedDate = cardDTO.getModifiedDate();
		createDate = new Date();
		updateDate = new Date();
		deleted = false;
		image = cardDTO.getUrl();
	}

	public boolean update(CardDTO cardDTO) {
		boolean update = false;
		
		if(!content.equals(cardDTO.getContent())){
			content = cardDTO.getContent();
			update = true;
		}
		
		if(!modifiedDate.equals(cardDTO.getModifiedDate())){
			content = cardDTO.getModifiedDate();
			update = true;
		}
		
//		if(images.){
//			이미지가 바뀌었을때
			update = true;
//		}

		return update;
	}

	public void getImageBig(){
		
	}
	
	public void getImageSmall(){
		
	}



}
