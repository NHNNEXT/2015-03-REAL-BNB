package com.babydear.model;

import java.util.Date;
import java.util.HashSet;
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

@Entity
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cId;

	@ManyToOne
	private Family fId;

	@OneToMany
	private Set<CardImage> images = new HashSet<CardImage>();

	@ManyToOne
	private User writer;

	private String content;
	private String modifiedDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false, updatable = false)
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_date", nullable = false)
	private Date updateDate;

	private boolean deleted;

	public void create(CardDTO cardDTO) {
		content = cardDTO.getContent();
		modifiedDate = cardDTO.getModifiedDate();
		createDate = new Date();
		updateDate = new Date();
		deleted = false;
		images.addAll(cardDTO.getImages());
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
			images.addAll(cardDTO.getImages());
			update = true;
//		}

		return update;
	}

	public Long getcId() {
		return cId;
	}

	public void setcId(Long cId) {
		this.cId = cId;
	}

	public Family getfId() {
		return fId;
	}

	public void setfId(Family fId) {
		this.fId = fId;
	}

	public Set<CardImage> getImages() {
		return images;
	}

	public void setImages(Set<CardImage> images) {
		this.images = images;
	}

	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
