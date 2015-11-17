package com.babydear.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.babydear.model.Baby;
import com.babydear.model.Card;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CardDTO {
	private Long cId;
	private String token;
	private String content;
	private String modifiedDate;
	private MultipartFile image;
	private List<Long> babies;
	
	public String getUrl() {
		return "img/asdf1234.jpg";
	}
	
	public void createCard(){
		Card card =  new Card();
		card.setContent(content);
		card.setImage(image.getOriginalFilename());
		card.setModifiedDate(modifiedDate);
		card.setDeleted(true);
		card.setCreateDate(new Date());
	}
}
