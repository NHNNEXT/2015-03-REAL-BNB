package com.babydear.component;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
import com.babydear.util.DateFormatter;

@Component
@PropertySource("classpath:config.properties")
public class InitDB {
	@Value("${ADMIN_TOKEN}") private String ADMIN_TOKEN;

	@Autowired UserRepository userRepo;
	@Autowired FamilyRepository familyRepo;
	@Autowired BabyRepository babyRepo;
	@Autowired AuthService authService;
	@Autowired CardRepository cardRepo;
	@Autowired DateFormatter f;

	@PostConstruct
	public void init() {
		User user1 = new User();
		user1.setEmail("a");
		user1.setPassword("a");
		user1.setUserRole("엄마");
		user1.setUserImg("/imgs/sample/user.png");
		user1 = userRepo.save(user1);
		
		User user2 = new User();
		user2.setEmail("papa@balbum.com");
		user2.setPassword("b");
		user2.setUserRole("아빠");
		user2.setUserImg("/imgs/sample/user2.png");
		user2 = userRepo.save(user2);

		Family family = new Family();
		family.setUpdateDate(new Date());
		family.setUpdateUId(user1.getUId());
		family = familyRepo.save(family);

		user1.setFId(family.getFId());
		user2.setFId(family.getFId());
		user1 = userRepo.save(user1);
		user2 = userRepo.save(user2);
		
		Baby baby1 = new Baby();
		baby1.setBabyBirth("2015-06-02");
		baby1.setBabyDate(f.calculateFromBabyToCard(baby1.getBabyBirth()));
		baby1.setBabyImg("/imgs/dummy/baby1.jpeg");
		baby1.setBabyGender(Baby.Gender.BOY);
		baby1.setBabyName("최예쁨");
		baby1.setFId(family.getFId());
		baby1 = babyRepo.save(baby1);

		Baby baby2 = new Baby();
		baby2.setBabyBirth("2010-12-03");
		baby2.setBabyDate(f.calculateFromBabyToCard(baby2.getBabyBirth()));
		baby2.setBabyImg("/imgs/dummy/baby2.jpeg");
		baby2.setBabyGender(Baby.Gender.GIRL);
		baby2.setBabyName("최똘똘");
		baby2.setFId(family.getFId());
		baby2 = babyRepo.save(baby2);

		Card card1 = new Card();
		card1.setBabies(Arrays.asList(baby2));
		card1.setCardImg("/imgs/dummy/photo1.jpg");
		card1.setModifiedDate("2014-04-30");
		card1.setContent("아장아장 걷는 우리아가, 쫌있음 펄쩍펄쩍 개구리처럼 뛰어 다니겠지? 상처나지 않게 조심히 다녀~");
		card1.setType(Card.Type.NORMAL);
		card1.setUId(user1.getUId());
		card1.setFId(family.getFId());
		card1.setCreateDate(new Date());
		card1.setUpdateDate(new Date()); 
		card1.setState(Card.State.Normal);
		card1.setLinkUrl(System.currentTimeMillis() + UUID.randomUUID().toString());
		cardRepo.save(card1);

		Card card2 = new Card();
		card2.setBabies(Arrays.asList(baby1));
		card2.setCardImg("/imgs/dummy/photo2.jpg");
		card2.setModifiedDate("2015-09-30");
		card2.setContent("웃는 모습이 너무 예뻐~ 엄마는 무척 행복해 이렇게 미소를 가득 머금고 있는 모습에 엄마는 항상 든든!");
		card2.setType(Card.Type.NORMAL);
		card2.setUId(user1.getUId());
		card2.setFId(family.getFId());
		card2.setCreateDate(new Date());
		card2.setUpdateDate(new Date());
		card2.setState(Card.State.Normal);
		card2.setLinkUrl(System.currentTimeMillis() + UUID.randomUUID().toString());
		cardRepo.save(card2);
		
		Card card3 = new Card();
		card3.setBabies(Arrays.asList(baby2));
		card3.setCardImg("/imgs/dummy/photo3.jpg");
		card3.setModifiedDate("2011-08-30");
		card3.setContent("아이고 우리 똘똘이 책도 야무지게 잘 읽는 구나 하지만 공부보다 건강이 우선이야~");
		card3.setType(Card.Type.NORMAL);
		card3.setUId(user1.getUId());
		card3.setFId(family.getFId());
		card3.setCreateDate(new Date());
		card3.setUpdateDate(new Date());
		card3.setState(Card.State.Normal);
		card3.setLinkUrl(System.currentTimeMillis() + UUID.randomUUID().toString());
		cardRepo.save(card3);
		
		Card card4 = new Card();
		card4.setBabies(Arrays.asList(baby2));
		card4.setCardImg("/imgs/dummy/photo4.jpg");
		card4.setModifiedDate("2015-08-30");
		card4.setContent("열심히 달리서 아빠에게로 달려가는 우리 아가! 엄마보다 아빠가 더 좋은게냐");
		card4.setType(Card.Type.NORMAL);
		card4.setUId(user1.getUId());
		card4.setFId(family.getFId());
		card4.setCreateDate(new Date());
		card4.setUpdateDate(new Date());
		card4.setState(Card.State.Normal);
		card4.setLinkUrl(System.currentTimeMillis() + UUID.randomUUID().toString());
		cardRepo.save(card4);
		
		Card card5 = new Card();
		card5.setBabies(Arrays.asList(baby1, baby2));
		card5.setCardImg("/imgs/dummy/photo5.jpg");
		card5.setModifiedDate("2016-01-05");
		card5.setContent("오늘 여행은 첫 가족여행이어서 너무 좋았어! 10년 20년 후에도 같이 또 가자");
		card5.setType(Card.Type.NORMAL);
		card5.setUId(user1.getUId());
		card5.setFId(family.getFId());
		card5.setCreateDate(new Date());
		card5.setUpdateDate(new Date());
		card5.setState(Card.State.Normal);
		card5.setLinkUrl(System.currentTimeMillis() + UUID.randomUUID().toString());
		cardRepo.save(card5);

		// Card card6 = new Card();
		// card6.setBabies(Arrays.asList(baby1, baby2));
		// card6.setCardImg("/imgs/dummy/photo6.jpg");
		// card6.setModifiedDate("2015-03-30");
		// card6.setContent("안녕 우리 아가 미소가 참 예뻐 무럭무럭 자라렴");
		// card6.setUId(user.getUId());
		// card6.setFId(family.getFId());
		// card6.setCreateDate(new Date());
		// card6.setUpdateDate(new Date());
		// card6.setDeleted(false);
		// cardRepo.save(card6);
		//
		// Card card7 = new Card();
		// card7.setBabies(Arrays.asList(baby2));
		// card7.setCardImg("/imgs/dummy/photo7.jpg");
		// card7.setModifiedDate("2015-03-30");
		// card7.setContent("높이 올라가. 세상을 다 가져봐. 네버 배끼레 배끼리럽. 네버 터니럽 터니리러업. 예에.
		// 할라 할로 할로 할로.");
		// card7.setUId(user.getUId());
		// card7.setFId(family.getFId());
		// card7.setCreateDate(new Date());
		// card7.setUpdateDate(new Date());
		// card7.setDeleted(false);
		// cardRepo.save(card7);
		//
		// Card card8 = new Card();
		// card8.setBabies(Arrays.asList(baby1));
		// card8.setCardImg("/imgs/dummy/photo8.jpg");
		// card8.setModifiedDate("2015-03-30");
		// card8.setContent("안녕 우리 아가 미소가 참 예뻐 무럭무럭 자라렴");
		// card8.setUId(user.getUId());
		// card8.setFId(family.getFId());
		// card8.setCreateDate(new Date());
		// card8.setUpdateDate(new Date());
		// card8.setDeleted(false);
		// cardRepo.save(card8);
		//
		// Card card9 = new Card();
		// card9.setBabies(Arrays.asList(baby1, baby2));
		// card9.setCardImg("/imgs/dummy/photo9.jpg");
		// card9.setModifiedDate("2015-03-30");
		// card9.setContent("안녕 우리 아가 미소가 참 예뻐 무럭무럭 자라렴");
		// card9.setUId(user.getUId());
		// card9.setFId(family.getFId());
		// card9.setCreateDate(new Date());
		// card9.setUpdateDate(new Date());
		// card9.setDeleted(false);
		// cardRepo.save(card9);

		authService.setUser(user1.getUId(), ADMIN_TOKEN);
	}
}
