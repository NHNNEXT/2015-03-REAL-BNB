package com.babydear.infra;

import java.util.Arrays;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.babydear.model.Baby;
import com.babydear.model.Card;
import com.babydear.model.Family;
import com.babydear.model.User;
import com.babydear.repository.BabyRepository;
import com.babydear.repository.CardRepository;
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
	@Autowired
	CardRepository cardRepo;
	
	
	@PostConstruct
	public void init(){
		User user = new User();
		user.setEmail("a");
		user.setPassword("a");
		user.setNickname("엄마");
		user.setUserImg("/imgs/sample/user.jpeg");
		user = userRepo.save(user);
		
		Family family = new Family();
		family.setUpdateDate(new Date());
		family.setUpdateUId(user.getUId());
		family = familyRepo.save(family);
		
		user.setFId(family.getFId());
		user = userRepo.save(user);
		
		Baby baby1 = new Baby();
		baby1.setBabyBirth("2015-02-02");
		baby1.setBabyImg("/imgs/dummy/baby1.jpeg");
		baby1.setBabyGender(Baby.Gender.GIRL);
		baby1.setBabyName("하채영");
		baby1.setFId(family.getFId());
		baby1 = babyRepo.save(baby1);
		
		Baby baby2 = new Baby();
		baby2.setBabyBirth("2016-01-03");
		baby2.setBabyImg("/imgs/dummy/baby2.jpeg");
		baby2.setBabyGender(Baby.Gender.PREGNANCY);
		baby2.setBabyName("꽁꽁이");
		baby2.setFId(family.getFId());
		baby2 = babyRepo.save(baby2);
		
		Card card1 = new Card();
		card1.setBabies(Arrays.asList(baby1, baby2));
		card1.setCardImg("/imgs/dummy/photo1.jpg");
		card1.setModifiedDate("2015-03-30");
		card1.setContent("안녕 우리 아가 미소가 참 예뻐 무럭무럭 자라렴");
		card1.setType(Card.Type.NORMAL);
		card1.setUid(user.getUId());
		card1.setFid(family.getFId());
		card1.setCreateDate(new Date());
		card1.setUpdateDate(new Date());
		card1.setDeleted(false);
		cardRepo.save(card1);
		
		Card card2 = new Card();
		card2.setBabies(Arrays.asList(baby1));
		card2.setCardImg("/imgs/dummy/photo2.jpg");
		card2.setModifiedDate("2015-03-30");
		card2.setContent("우리 사랑하는 뿅이 안녕!!벌써 새해 구나."
				+ "우리 뿅이를 만날날도 얼마 남지 않았다는걸 생각하니"
				+ "본격적으로 두근거리고 설레이는 구나. "
				+ "그동안 왜 진작 너에게 일기를 써보지 않았을까 하는 후회와 함께"
				+ "너를 맞이 하기 전까지 좀더 부지런한 엄마가 되기로 마음 먹었단다. "
				+ "화이팅 ~!!");
		card2.setType(Card.Type.NORMAL);
		card2.setUid(user.getUId());
		card2.setFid(family.getFId());
		card2.setCreateDate(new Date());
		card2.setUpdateDate(new Date());
		card2.setDeleted(false);
		cardRepo.save(card2);
		
//		
//		Card card3 = new Card();
//		card3.setBabies(Arrays.asList(baby1, baby2));
//		card3.setCardImg("/imgs/dummy/photo3.jpg");
//		card3.setModifiedDate("2015-03-30");
//		card3.setContent("오늘은 니가 생긴지 26주하고 5일째 되는 날이란다. /n"
//				+ "엄마 배가 드럼이 되어 잠을 못자도 좋으니 뱃속에서 활발하게 /n"
//				+ "놀아다오./n");
//		card3.setType(Card.Type.NORMAL);
//		card3.setUId(user.getUId());
//		card3.setFId(family.getFId());
//		card3.setCreateDate(new Date());
//		card3.setUpdateDate(new Date());
//		card3.setDeleted(false);
//		cardRepo.save(card3);
//		
//		Card card4 = new Card();
//		card4.setBabies(Arrays.asList(baby2));
//		card4.setCardImg("/imgs/dummy/photo4.jpg");
//		card4.setModifiedDate("2015-03-30");
//		card4.setContent("안녕 우리 아가 미소가 참 예뻐 무럭무럭 자라렴");
//		card4.setType(Card.Type.NORMAL);
//		card4.setUId(user.getUId());
//		card4.setFId(family.getFId());
//		card4.setCreateDate(new Date());
//		card4.setUpdateDate(new Date());
//		card4.setDeleted(false);
//		cardRepo.save(card4);
//	
//		Card card5 = new Card();
//		card5.setBabies(Arrays.asList(baby1, baby2));
//		card5.setCardImg("/imgs/dummy/photo5.jpg");
//		card5.setModifiedDate("2015-03-30");
//		card5.setContent("안녕 우리 아가 미소가 참 예뻐 무럭무럭 자라렴");
//		card5.setUId(user.getUId());
//		card5.setFId(family.getFId());
//		card5.setCreateDate(new Date());
//		card5.setUpdateDate(new Date());
//		card5.setDeleted(false);
//		cardRepo.save(card5);
//
//		Card card6 = new Card();
//		card6.setBabies(Arrays.asList(baby1, baby2));
//		card6.setCardImg("/imgs/dummy/photo6.jpg");
//		card6.setModifiedDate("2015-03-30");
//		card6.setContent("안녕 우리 아가 미소가 참 예뻐 무럭무럭 자라렴");
//		card6.setUId(user.getUId());
//		card6.setFId(family.getFId());
//		card6.setCreateDate(new Date());
//		card6.setUpdateDate(new Date());
//		card6.setDeleted(false);
//		cardRepo.save(card6);
//
//		Card card7 = new Card();
//		card7.setBabies(Arrays.asList(baby2));
//		card7.setCardImg("/imgs/dummy/photo7.jpg");
//		card7.setModifiedDate("2015-03-30");
//		card7.setContent("높이 올라가. 세상을 다 가져봐. 네버 배끼레 배끼리럽. 네버 터니럽 터니리러업. 예에. 할라 할로 할로 할로.");
//		card7.setUId(user.getUId());
//		card7.setFId(family.getFId());
//		card7.setCreateDate(new Date());
//		card7.setUpdateDate(new Date());
//		card7.setDeleted(false);
//		cardRepo.save(card7);
//		
//		Card card8 = new Card();
//		card8.setBabies(Arrays.asList(baby1));
//		card8.setCardImg("/imgs/dummy/photo8.jpg");
//		card8.setModifiedDate("2015-03-30");
//		card8.setContent("안녕 우리 아가 미소가 참 예뻐 무럭무럭 자라렴");
//		card8.setUId(user.getUId());
//		card8.setFId(family.getFId());
//		card8.setCreateDate(new Date());
//		card8.setUpdateDate(new Date());
//		card8.setDeleted(false);
//		cardRepo.save(card8);
//		
//		Card card9 = new Card();
//		card9.setBabies(Arrays.asList(baby1, baby2));
//		card9.setCardImg("/imgs/dummy/photo9.jpg");
//		card9.setModifiedDate("2015-03-30");
//		card9.setContent("안녕 우리 아가 미소가 참 예뻐 무럭무럭 자라렴");
//		card9.setUId(user.getUId());
//		card9.setFId(family.getFId());
//		card9.setCreateDate(new Date());
//		card9.setUpdateDate(new Date());
//		card9.setDeleted(false);
//		cardRepo.save(card9);
		
		String token = authService.setUser(user.getUId(), "token");
	}
}
