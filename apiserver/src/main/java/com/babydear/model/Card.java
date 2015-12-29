package com.babydear.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Card {
	public enum Type {
		BAD,
		NORMAL,
		EVENT
	}
	public enum State {
		Normal,
		Deleted
	}
	// private Long cId = new Long(1);
	// private String content ="이쁜 우리 아기 걸음마 시작! 축하해 뿅뿅아";
	// private String modifiedDate ="2010-10-10";
	// private String imgUrl = "/imgs/sample/card.jpeg";
	// private List<Long> babies = Arrays.asList(new Long(1),new Long(1),new
	// Long(1));
	// }
	@Id @GeneratedValue(strategy = GenerationType.AUTO) 
	private Long cId;
	private Long fId;
	private Long uId;

	@Transient
	private List<Long> bIds;
//	@Transient
	@ManyToMany
	private List<Baby> babies;

	private String cardImg;
	@Lob
	@Column( length = 100000 )
	private String content;
	private String modifiedDate;

	@Temporal(TemporalType.TIMESTAMP) private Date createDate;
	@Temporal(TemporalType.TIMESTAMP) private Date updateDate;

	private State state;
	private Type type;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bIds == null) ? 0 : bIds.hashCode());
		return result;
	}
	
	
	// public Card() {
	// }
	// public Card(Family fId, User writer, Set<Baby> babies, String image,
	// String content, String modifiedDate) {
	// super();
	// this.fId = fId;
	// this.writer = writer;
	// this.babies = babies;
	// this.image = image;
	// this.content = content;
	// this.modifiedDate = modifiedDate;
	// }
	//
	// public void create(CardDTO cardDTO) {
	// content = cardDTO.getContent();
	// modifiedDate = cardDTO.getModifiedDate();
	// createDate = new Date();
	// updateDate = new Date();
	// deleted = false;
	// image = cardDTO.getUrl();
	// }
	//
	// public boolean update(CardDTO cardDTO) {
	// boolean update = false;
	//
	// if(!content.equals(cardDTO.getContent())){
	// content = cardDTO.getContent();
	// update = true;
	// }
	//
	// if(!modifiedDate.equals(cardDTO.getModifiedDate())){
	// content = cardDTO.getModifiedDate();
	// update = true;
	// }
	//
	//// if(images.){
	//// 이미지가 바뀌었을때
	// update = true;
	//// }
	//
	// return update;
	// }
	//
	// public void getImageBig(){
	//
	// }
	//
	// public void getImageSmall(){
	//
	// }

}
