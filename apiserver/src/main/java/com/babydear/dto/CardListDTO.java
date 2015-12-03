package com.babydear.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.babydear.model.Baby;
import com.babydear.model.Card;
import com.babydear.model.Family;
import com.babydear.model.User;
import com.mysql.fabric.xmlrpc.base.Array;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CardListDTO {
	private String error = "adsf";
	private Integer startCard = 10;
	public Integer count = 10;
	public List<CardShowDTO> cardList = Arrays.asList(
			new CardShowDTO(),new CardShowDTO());
}
