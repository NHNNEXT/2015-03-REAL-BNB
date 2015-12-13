package com.babydear.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.babydear.ApiserverApplication;
import com.babydear.dto.UserDTO;
import com.babydear.model.Baby;
import com.babydear.model.Card;
import com.babydear.model.Family;
import com.babydear.model.User;
import com.babydear.repository.BabyRepository;
import com.babydear.repository.CardRepository;
import com.babydear.repository.FamilyRepository;
import com.babydear.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApiserverApplication.class)
public class RepoTest {
	@Autowired
	private FamilyRepository familyRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BabyRepository babyRepo;
	@Autowired
	private CardRepository cardRepo;

	@Test
	public void createUserByInviteWithBabies() throws Exception {
		Family family = new Family();
		family = familyRepo.save(family);
		
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("erin314@naver.com");
		userDTO.setPassword("1234");
		userDTO.setNickname("mome");
		userDTO.setFId(family.getFId());
		
		User user = new User(userDTO);
		userRepo.save(user);
		
		Baby baby = new Baby();
//		baby.setBabyBirth(new Date());
		baby.setBabyName("dumdum");
		baby.setBabySex(Baby.Gender.BOY);
//		baby.setFid(family);
		
		Baby baby2 = new Baby();
//		baby2.setBabyBirth(new Date());
		baby2.setBabyName("dumdum");
		baby2.setBabySex(Baby.Gender.GIRL);
//		baby2.setFid(family);
		
		List<Baby> babies = Arrays.asList(baby, baby2);
		babyRepo.save(babies);

		Baby temp =babyRepo.findOne(new Long(1));
		Card card = new Card();
		Set<Baby> set = new HashSet<Baby>();
		set.add(temp);
//		card.setBabies(set);
		card.setContent("content");
		card.setCreateDate(new Date());
		card.setUpdateDate(new Date());
		card.setDeleted(new Boolean(false));
//		card.setFId(family);
		card.setModifiedDate("2015-03-13");
		
		Card card1 = new Card();
		Set<Baby> set1 = new HashSet<Baby>();
		set1.add(temp);
//		card1.setBabies(set1);
		card1.setContent("content");
		card1.setUpdateDate(new Date());
		card1.setCreateDate(new Date());
		card1.setDeleted(new Boolean(true));
//		card1.setFId(family);
		card1.setModifiedDate("2015-03-13");
		cardRepo.save(card1);
		
		Card card2 = cardRepo.findOne(new Long(1));
		Card card3 = cardRepo.findOne(new Long(1));
		
		System.out.println("-------"+card2.getContent());
//		System.out.println("-------"+card3);
//		System.out.println(card3);
		
	}
}
