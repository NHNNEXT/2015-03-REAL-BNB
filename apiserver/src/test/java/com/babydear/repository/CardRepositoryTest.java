package com.babydear.repository;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.babydear.ApiserverApplication;
import com.babydear.model.Card;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApiserverApplication.class)
public class CardRepositoryTest {

	@Autowired
	CardRepository repo;
	Card result;

	@Test
	public void testForRepo() {
		String email = "erin3143@naver.com";
		Card card = new Card();
//		card.setModi(email);
//		card.setName("erin");
//		card.setPasswd("1234");
		
		result = repo.save(card);
		System.out.println(result);
		System.out.println("dddddd");
		
//		result = repo.findByEmail(email);
//		result.setName("hello");
		System.out.println(result);
		result = repo.save(result);
		System.out.println(result);
		
//		result = repo.findByEmail("dodoododododdododo");
		System.out.println(result);
	}

}
