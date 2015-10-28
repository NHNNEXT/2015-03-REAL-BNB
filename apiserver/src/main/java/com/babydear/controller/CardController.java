package com.babydear.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.babydear.dto.CardDTO;
import com.babydear.model.Card;

@RestController
//@RequestMapping(value = "/api/card")
public class CardController {
	
	@RequestMapping(value = "/api/card",  method = RequestMethod.GET)
	public String selectCards(){
		return "{ error: null, startCard: 10, count: 10, cards: [a,b,c,d]}";
	}
	@RequestMapping(value = "/api/card", method = RequestMethod.POST)
	public Card createCard(CardDTO cardDTO){
		Card card = new Card(cardDTO);
		
		System.out.println("--------*********--------*********--------*********");
		System.out.println(cardDTO.getFiles().size());
		System.out.println(cardDTO);
		System.out.println(card);
//		return "{ create : true, error: null }";
		return card;
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
