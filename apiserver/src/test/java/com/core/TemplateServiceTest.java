package com.core;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.babydear.model.Baby;
import com.babydear.model.Card;
import com.babydear.service.TemplateService;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = ApiserverApplication.class)
//@WebAppConfiguration
public class TemplateServiceTest {
	
	
	@Test
	public void imgService() throws Exception {
		TemplateService s = new TemplateService();
		s.setFILE_STORAGE_DIRECTORY("/Users/erin/git/2015-03-REAL-BEAUTIES-NB/apiserver/src/main/webapp/");
		
//		s.processTags(Arrays.asList(new Long(1),new Long(1),new Long(1)));
		s.setCards(initdb());
		
		
	}
	public List<Card> initdb(){
		Baby baby1 = new Baby();
		baby1.setBabyBirth("2015-02-02");
		baby1.setBabyImg("/imgs/dummy/baby1.jpeg");
		baby1.setBabyGender(Baby.Gender.GIRL);
		baby1.setBabyName("하채영");

		Baby baby2 = new Baby();
		baby2.setBabyBirth("2016-01-03");
		baby2.setBabyImg("/imgs/dummy/baby2.jpeg");
		baby2.setBabyGender(Baby.Gender.PREGNANCY);
		baby2.setBabyName("꽁꽁이");

		Card card1 = new Card();
		card1.setBabies(Arrays.asList(baby1, baby2));
		card1.setCardImg("/imgs/dummy/photo1.jpg");
		card1.setModifiedDate("2015-03-30");
		card1.setContent("안녕 우리 아가 미소가 참 예뻐 무럭무럭 자라렴");
		card1.setType(Card.Type.NORMAL);
		card1.setCreateDate(new Date());
		card1.setUpdateDate(new Date());
		card1.setState(Card.State.Normal);

		Card card2 = new Card();
		card2.setBabies(Arrays.asList(baby1));
		card2.setCardImg("/imgs/dummy/photo2.jpg");
		card2.setModifiedDate("2015-03-30");
		card2.setContent("우리 사랑하는 뿅이 안녕!!벌써 새해 구나." + "우리 뿅이를 만날날도 얼마 남지 않았다는걸 생각하니" + "본격적으로 두근거리고 설레이는 구나. "
				+ "그동안 왜 진작 너에게 일기를 써보지 않았을까 하는 후회와 함께" + "너를 맞이 하기 전까지 좀더 부지런한 엄마가 되기로 마음 먹었단다. " + "화이팅 ~!!");
		card2.setType(Card.Type.NORMAL);
		card2.setCreateDate(new Date());
		card2.setUpdateDate(new Date());
		card2.setState(Card.State.Normal);
		return Arrays.asList(card1, card2);
	}
}
