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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import com.babydear.repository.BabyRepository;
import com.babydear.repository.CardRepository;
import com.babydear.service.AuthService;
import com.babydear.service.ImgService;
import com.babydear.service.TagService;
import com.mysql.fabric.Response;

import sun.net.www.protocol.http.AuthScheme;

@RestController
// @RequestMapping(value = "/api/card")
public class CardFilterController {
	private static final Logger logger = LoggerFactory.getLogger(CardFilterController.class);
	@Autowired CardRepository cardRepo;
	@Autowired TagService tagService;
	@Autowired AuthService authService;
	@Autowired ImgService imgService;
	@Autowired BabyRepository babyRepo;

	@RequestMapping(value = "/api/filter", method = RequestMethod.GET)
	public CardListDTO showCardFilter(String token) {
		if (token == null || token.isEmpty()) return new CardListDTO("토큰이 없습니다.");
		try {
			User user = authService.getUser(token);
			List<Card> cardResponseList = cardRepo.findByStateAndFIdOrderByCIdAsc(Card.State.Normal, user.getFId());
			CardListDTO cardListDTO = new CardListDTO();
			cardListDTO.setCardList(cardResponseList);
			return cardListDTO;
		} catch (NotToken e) {
			return new CardListDTO("유효하지 않은 토큰 입니다.");
		}
	}

	@RequestMapping(value = "/api/filter/baby", method = RequestMethod.GET)
	public CardListDTO showCardFilterByBaby(String token, Long bId) {
		if (token == null || token.isEmpty()) return new CardListDTO("토큰이 없습니다.");
		try {
			User user = authService.getUser(token);
			List<Card> cardList = cardRepo.findByStateAndFIdOrderByCIdAsc(Card.State.Normal, user.getFId());
			Baby baby = babyRepo.getOne(bId);
			// List<Card> cardResponseList =
			// cardRepo.findByStateAndFIdAndBIdOrderByCIdAsc(Card.State.Normal,
			// user.getFId(), bId);
			List<Card> cardResponseList = new ArrayList<>();
			System.out.println(cardList.size());
			System.out.println(cardResponseList.size());
			for (Card card : cardList) {
//				card.getBabies().
				if (card.getBabies().contains(new Baby(bId))) {
					cardResponseList.add(card);
				}else{
					System.out.println("why"+card);
				}
			}
			System.out.println(cardResponseList.size());
			CardListDTO cardListDTO = new CardListDTO();
			cardListDTO.setCardList(cardResponseList);
			return cardListDTO;
		} catch (NotToken e) {
			return new CardListDTO("유효하지 않은 토큰 입니다.");
		}
	}

	@RequestMapping(value = "/api/filter/babies", method = RequestMethod.GET)
	public CardListDTO showCardFilterByBabies(@RequestBody Map<String, Object> req) {
		List<Integer> babies = (ArrayList<Integer>) req.get("babies");
		String token = (String) req.get("token");
		if(token == null || token.isEmpty()) return new CardListDTO("토큰이 없습니다.");
		try {
			User user = authService.getUser(token);
			List<Card> cardList = cardRepo.findByStateAndFIdOrderByCIdAsc(Card.State.Normal, user.getFId());
//			List<Card> cardResponseList = cardRepo.findByStateAndFIdAndBIdOrderByCIdAsc(Card.State.Normal, user.getFId(), bId);
			List<Card> cardResponseList = new ArrayList<>();
			for(Card card : cardList){
				for(Integer bId : babies){
					if(card.getBabies().contains(new Baby(bId))){
						cardResponseList.add(card);
						break;
					}
				}
			}
			CardListDTO cardListDTO = new CardListDTO();
			cardListDTO.setCardList(cardResponseList);
			return cardListDTO;
		} catch (NotToken e) {
			return new CardListDTO("유효하지 않은 토큰 입니다.");
		}
	}

	@RequestMapping(value = "/api/filter/list", consumes = "application/json")
	public CardListDTO showCardFilterByCIds(@RequestBody Map<String, Object> req) {
		List<Integer> list = (ArrayList<Integer>) req.get("cardIds");
		// logger.info(list.get(0).toString());
		List<Card> cardResponseList = new ArrayList<Card>();
		CardListDTO cardListDTO = new CardListDTO();
		for (Integer cId : list) {
			cardResponseList.add(cardRepo.getOne(cId.longValue()));
		}
		cardListDTO.setCardList(cardResponseList);
		logger.info(cardResponseList.size() + "");
		return cardListDTO;
	}
}
