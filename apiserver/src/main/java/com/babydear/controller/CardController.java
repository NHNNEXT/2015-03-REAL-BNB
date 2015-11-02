package com.babydear.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.babydear.dto.CardDTO;
import com.babydear.model.Card;
import com.babydear.repository.CardRepository;

@RestController
//@RequestMapping(value = "/api/card")
public class CardController {
	@Autowired
	CardRepository cardRepo;
	
	@RequestMapping("/set")
	public Set hello(){
		Set set = new HashSet();
		set.add("b1.jpg");
		set.add("b2.jpg");
		set.add("b3.jpg");
		set.add("b4.jpg");
		return set;
	}
	
	@RequestMapping(value = "/api/card",  method = RequestMethod.GET)
	public String selectCards(){
		return "{ error: null, startCard: 10, count: 10, cards: [a,b,c,d]}";
	}
	@RequestMapping(value = "/api/card", method = RequestMethod.POST)
	public Card createCard(CardDTO cardDTO){
		Assert.notNull(cardDTO, "card should be not null!");
		Card card = new Card();
		card.create(cardDTO);
		
		cardRepo.save(card);
		
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
