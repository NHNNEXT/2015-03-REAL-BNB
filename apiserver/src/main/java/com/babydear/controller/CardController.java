package com.babydear.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.babydear.dto.ResponseDTO;
import com.babydear.dto.CardListDTO;
import com.babydear.dto.ResponseDTO;
import com.babydear.exception.NotGoodExtention;
import com.babydear.exception.NotToken;
import com.babydear.model.Baby;
import com.babydear.model.Card;
import com.babydear.model.User;
import com.babydear.repository.CardRepository;
import com.babydear.service.AuthService;
import com.babydear.service.ImgService;
import com.babydear.service.TagService;
import com.mysql.fabric.Response;

import sun.net.www.protocol.http.AuthScheme;

@RestController
//@RequestMapping(value = "/api/card")
public class CardController {
	private static final Logger logger = LoggerFactory.getLogger(CardController.class);
	@Autowired
	CardRepository cardRepo;
	@Autowired
	TagService tagService;
	@Autowired AuthService authService;
	
	@Autowired
	ImgService imgService;
	
	@RequestMapping(value = "/api/card",  method = RequestMethod.GET)
	public CardListDTO selectCards(String token){
		if (token == null || token.isEmpty()) return new CardListDTO("토큰이 없습니다.");
		try {
			User user = authService.getUser(token);
			List<Card> cardResponseList = cardRepo.findByStateAndFIdOrderByCIdDesc(Card.State.Normal, user.getFId());
			CardListDTO cardListDTO = new CardListDTO();
			cardListDTO.setCardList(cardResponseList);
			return cardListDTO;
		} catch (NotToken e) {
			return new CardListDTO("유효하지 않은 토큰 입니다.");
		}
	}
	
	@RequestMapping(value = "/api/card", method = RequestMethod.POST)
	public ResponseDTO createCard(String token, Card card, MultipartFile image){
		logger.info(card.toString());
		if(token == null || token.isEmpty()) return new ResponseDTO(false, "토큰이 없습니다.");
		try {
			User user = authService.getUser(token);
			card.setFId(user.getFId());
			card.setUId(user.getUId());
		} catch (NotToken e1) {
			return new ResponseDTO(false, "토큰이 유효하지 않습니다.");
		}
		final List<Baby> babies = tagService.processTags(card.getBIds(), card.getBabies());
		card.setBabies(babies);
		card.setState(Card.State.Normal);
		card.setCreateDate(new Date());
		card.setUpdateDate(new Date());
		
		try {
			card.setCardImg(imgService.processImgCard(image));
		} catch (IllegalStateException e) {
			return new ResponseDTO(false, "이미지가 너무 크거나 잘못되었습니다.");
		} catch (IOException e) {
			return new ResponseDTO(false, "이미지가 너무 크거나 잘못되었습니다.");
		} catch (NotGoodExtention e) {
			return new ResponseDTO(false, "이미지 형식이 잘못 되었습니다.");
		}
		card = cardRepo.save(card);
		return new ResponseDTO(true, "null", card);
	}
	
	@RequestMapping(value = "/api/card/update", method = RequestMethod.POST)
	public ResponseDTO updateCard(String token, Card cardDTO, MultipartFile image){
		logger.info(cardDTO.toString());
		if(token == null || token.isEmpty()) return new ResponseDTO(false, "토큰이 없습니다.");
		if(cardDTO.getCId() == null || cardDTO.getCId() == 0) return new ResponseDTO(false, "수정할 카드의 id가 없습니다.");
		Card card = cardRepo.findOne(cardDTO.getCId());
		final List<Baby> babies = tagService.processTags(cardDTO.getBIds(), cardDTO.getBabies());
		card.setBabies(babies);
		card.setUpdateDate(new Date());
		try {
			if(!(image == null || image.isEmpty()))
				cardDTO.setCardImg(imgService.processImgCard(image));
		} catch (IllegalStateException e) {
			return new ResponseDTO(false, "이미지가 너무 크거나 잘못되었습니다.");
		} catch (IOException e) {
			return new ResponseDTO(false, "이미지가 너무 크거나 잘못되었습니다.");
		} catch (NotGoodExtention e) {
			return new ResponseDTO(false, "이미지 형식이 잘못 되었습니다.");
		}
		card = cardRepo.save(card);
		return new ResponseDTO(true, "null", card);
	}
	@RequestMapping(value = "/api/card", method = RequestMethod.PUT)
	public ResponseDTO putCard(String token, Card cardDTO, MultipartFile image){
		logger.info(cardDTO.toString());
		if(token == null || token.isEmpty()) return new ResponseDTO(false, "토큰이 없습니다.");
		if(cardDTO.getCId() == null || cardDTO.getCId() == 0) return new ResponseDTO(false, "수정할 카드의 id가 없습니다.");
		Card card = cardRepo.findOne(cardDTO.getCId());
		final List<Baby> babies = tagService.processTags(cardDTO.getBIds(), cardDTO.getBabies());
		card.setBabies(babies);
		card.setUpdateDate(new Date());
		try {
			if(!(image == null || image.isEmpty()))
				cardDTO.setCardImg(imgService.processImgCard(image));
		} catch (IllegalStateException e) {
			return new ResponseDTO(false, "이미지가 너무 크거나 잘못되었습니다.");
		} catch (IOException e) {
			return new ResponseDTO(false, "이미지가 너무 크거나 잘못되었습니다.");
		} catch (NotGoodExtention e) {
			return new ResponseDTO(false, "이미지 형식이 잘못 되었습니다.");
		}
		card = cardRepo.save(card);
		return new ResponseDTO(true, "null", card);
	}
	
	@RequestMapping(value = "/api/card/delete", method = RequestMethod.GET)
	public ResponseDTO deleteCard(Long cId){
		Card card = cardRepo.findOne(cId);
		if(card == null) return new ResponseDTO(false, "해당 카드가 없어요", card);
		card.setState(Card.State.Deleted);
		card = cardRepo.save(card);
		return new ResponseDTO(true, "null", card);
	}
	@RequestMapping(value = "/api/card", method = RequestMethod.DELETE)
	public ResponseDTO deleteCId(Long cId){
		if(cId== null) return new ResponseDTO(false, "카드 id 를 입력해 주세요");
		Card card = cardRepo.findOne(cId);
		if(card == null) return new ResponseDTO(false, "해당 카드가 없어요 카드 아이디가 맞나요? ID:"+cId, card);
		card.setState(Card.State.Deleted);
		card = cardRepo.save(card);
		return new ResponseDTO(true, "null", card);
	}
	
	@RequestMapping(value="/api/card/{cId}")
	public Card getOne(@PathVariable("cId")Long cId){
		logger.info("hello");
		logger.info("show one card:"+cId);
		return cardRepo.findOne(cId);
	}
	
	@RequestMapping(value="/api/card/list", consumes ="application/json")
		public CardListDTO getCardList(@RequestBody Map<String, Object> req){
		List<Integer> list = (ArrayList<Integer>) req.get("cardIds");
//		logger.info(list.get(0).toString());
		List<Card> cardResponseList = new ArrayList<Card>();
		CardListDTO cardListDTO = new CardListDTO();
		for(Integer cId : list){
			Card card = cardRepo.getOne(cId.longValue());
			if(card == null) continue;
			cardResponseList.add(card);
		}
		cardListDTO.setCardList(cardResponseList);
//		logger.info(cardResponseList.get(0)+"");
//		logger.info(cardResponseList.size()+"");
		return cardListDTO;
	}
}
