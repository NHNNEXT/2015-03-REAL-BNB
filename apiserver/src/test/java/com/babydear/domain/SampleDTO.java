package com.babydear.domain;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.mock.web.MockMultipartFile;

import com.babydear.dto.CardDTO;

public class SampleDTO {
	CardDTO card;
	
	CardDTO getCard() throws IOException{
		if(card == null){
			FileInputStream inputFile = new FileInputStream("src/main/resources/static/asdf.jpeg");
			MockMultipartFile image = new MockMultipartFile("file", "asdf.jpeg", "multipart/form-data", inputFile);
			
			card = new CardDTO();
			card.setContent("hello");
			card.setModifiedDate("1994-04-14");
//			card.setBabies(new Set());
			card.setImage(image);
		}
		return card;
	}
}
