package com.babydear.controller;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.babydear.dto.CardDTO;
import com.babydear.dto.CardListDTO;
import com.babydear.dto.ResponseDTO;
import com.babydear.model.Baby;
import com.babydear.model.Card;
import com.babydear.model.User;
import com.babydear.repository.CardRepository;
import com.babydear.service.ImgService;
import com.babydear.service.TagService;
import com.mysql.fabric.Response;


@RestController
//@RequestMapping(value = "/api/card")
public class CardController {
	@Autowired
	CardRepository cardRepo;
	@Autowired
	TagService tagService;
	
	@Autowired
	ImgService imgService;
	
	@RequestMapping(value = "/api/card",  method = RequestMethod.GET)
	public CardListDTO selectCards(Long uId, User user){
		System.out.println("it's uId");
//		System.out.println(uId);
//		System.out.println(user.getUId());
		
		
		return new CardListDTO();
	}
	@RequestMapping(value = "/api/card", method = RequestMethod.POST)
	public ResponseDTO createCard(User user, CardDTO cardDTO){
		System.out.println("hello im card card ");
		Assert.notNull(cardDTO, "card should be not null!");
//		console.log(cardDTO);
		System.out.println(cardDTO);
		final Set<Baby> babies = tagService.processTags(cardDTO.getBabies());
		final String image = imgService.processImg(cardDTO.getImage());
//		Card card = new Card(user.getFamily(), user, babies, image, cardDTO.getContent(), cardDTO.getModifiedDate());
//		cardRepo.save(card);
//		return "{ create : true, error: null }";
//		return card;
		ResponseDTO res = new ResponseDTO();
		res.setState(true);
		res.setError("null");
		return res;
	}
	@RequestMapping(value = "/api/card", method = RequestMethod.PUT)
	public String updateCard(){
		return " { update : true, error: null }";
	}
	@RequestMapping(value = "/api/card", method = RequestMethod.DELETE)
	public String deleteCard(){
		return " { delete: true, error: null }";
	}

}
