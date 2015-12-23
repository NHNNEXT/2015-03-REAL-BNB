package com.babydear.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
public class BabyTest {
	@Autowired
	private FamilyRepository familyRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BabyRepository babyRepo;
	@Autowired
	private CardRepository cardRepo;
	
	@Test
	public void createUserByFirstWithNoBaby() throws Exception {
//		List<Card> list = cardRepo.findAllByBabies();
//		System.out.println(list);
	}
}
