package com.babydear.infra;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.babydear.model.Baby;
import com.babydear.model.Card;
import com.babydear.model.Family;
import com.babydear.model.User;
import com.babydear.repository.BabyRepository;
import com.babydear.repository.FamilyRepository;
import com.babydear.repository.UserRepository;
import com.babydear.service.AuthService;

@Component
public class InitDB {

	@Autowired
	UserRepository userRepo;
	@Autowired
	FamilyRepository familyRepo;
	@Autowired
	BabyRepository babyRepo;
	@Autowired
	AuthService authService;
	
	
	@PostConstruct
	public void init(){
		User user = new User();
		user.setEmail("erin314@naver.com");
		user.setPassword("1234");
		user.setNickname("엄마");
		user.setUserImg("/imgs/sample/user.jpeg");
		user = userRepo.save(user);
		
		Family family = new Family();
		family.setUpdateDate(new Date());
		family.setUpdateUId(user.getUId());
		family = familyRepo.save(family);
		
		user.setFId(family.getFId());
		user = userRepo.save(user);
		
		Baby baby = new Baby();
		baby.setBabyBirth("2015-02-02");
		baby.setBabyImg("/imgs/sample/baby.jpeg");
		baby.setBabySex(Baby.Sex.GIRL);
		baby.setBabyName("하채영");
		baby.setFId(family.getFId());
		baby = babyRepo.save(baby);
		
		Card card = new Card();
		card.setContent("안녕 우리 아가 미소가 참 예뻐 무럭무럭 자라렴");
		card.setCreateDate(new Date());
		
		
		String token = authService.setUser(user.getUId(), "1234");
	}
}
