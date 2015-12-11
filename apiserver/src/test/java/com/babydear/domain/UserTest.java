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
import com.babydear.model.Family;
import com.babydear.model.User;
import com.babydear.repository.BabyRepository;
import com.babydear.repository.FamilyRepository;
import com.babydear.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApiserverApplication.class)
public class UserTest {
	@Autowired
	private FamilyRepository familyRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BabyRepository babyRepo;

	@Test
	public void createUserByFirstWithNoBaby() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("erin314@naver.com");
		userDTO.setPassword("1234");
		userDTO.setNickname("mome");

		Family family = new Family();
		family = familyRepo.save(family);

		User user = new User(userDTO, null);
		userRepo.save(user);
	}

	@Test
	public void createUserByFirstWithBabies() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("erin314@naver.com");
		userDTO.setPassword("1234");
		userDTO.setNickname("mome");

		Baby baby = new Baby();
		baby.setBabyName("dumdum");
		List<Baby> babies = Arrays.asList(baby, baby);
//		userDTO.setBabies(babies);

		Family family = new Family();
		family = familyRepo.save(family);

		User user = new User(userDTO, null);
		userRepo.save(user);
//		babyRepo.save(userDTO.getBabies());

	}

	@Test
	public void createUserByInviteWithNoBaby() throws Exception {
		Family family = new Family();
		family = familyRepo.save(family);
		
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("erin314@naver.com");
		userDTO.setPassword("1234");
		userDTO.setNickname("mome");
		userDTO.setFId(family.getFId());

		User user = new User(userDTO);
		userRepo.save(user);
	}
	@Test
	public void createUserByInviteWithNoBaby_noFamily() throws Exception {
		Family family = new Family();
		family = familyRepo.save(family);
		
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("erin314@naver.com");
		userDTO.setPassword("1234");
		userDTO.setNickname("mome");
		userDTO.setFId(new Long(1234));

		User user = new User(userDTO, null);
		userRepo.save(user);
	}

	@Test
	public void createUserByInviteWithBabies() throws Exception {
		Family family = new Family();
		family = familyRepo.save(family);
		
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("erin314@naver.com");
		userDTO.setPassword("1234");
		userDTO.setNickname("mome");
		userDTO.setFId(family.getFId());
		
		Baby baby = new Baby();
//		baby.setBabyBirth(new Date());
		baby.setBabyName("dumdum");
		List<Baby> babies = Arrays.asList(baby, baby);
//		userDTO.setBabies(babies);

		User user = new User(userDTO);
		userRepo.save(user);
		babyRepo.save(babies);
	}
	
	@Test
	public void loginUser() throws Exception {
		Family family = new Family();
		family = familyRepo.save(family);
		
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("erin314@naver.com");
		userDTO.setPassword("1234");
		userDTO.setNickname("mome");
		userDTO.setFId(family.getFId());
		User user = new User(userDTO);
		userRepo.save(user);
		
		UserDTO loginDTO = new UserDTO();
		loginDTO.setEmail("erin314@naver.com");
		loginDTO.setPassword("1234");
		User login = new User(loginDTO);
		login = userRepo.findByEmail(login.getEmail());
		
	}
}
