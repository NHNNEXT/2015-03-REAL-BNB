package com.babydear.repository;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.babydear.ApiserverApplication;
import com.babydear.model.Card;
import com.babydear.model.Family;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApiserverApplication.class)
public class CardRepositoryTest {

	@Autowired
	CardRepository repo;
	
	Family family;
	Card result;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}	

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
