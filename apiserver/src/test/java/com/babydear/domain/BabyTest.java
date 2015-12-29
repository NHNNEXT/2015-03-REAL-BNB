package com.babydear.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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

public class BabyTest {
	@Test
	public void createUserByFirstWithNoBaby() throws Exception {
		Baby baby1 = new Baby(new Long(1));
		baby1.setBId(new Long(1));
		List<Baby> babies = new ArrayList<Baby>();
		babies.add(new Baby(new Long(1)));
		
		if (babies.contains(baby1)) {
			System.out.println("true");
		}else{
			System.out.println("false");
		}
	}
	
	@Test
	public void testDateFormating() throws Exception {
		Baby baby = new Baby(new Long(1));
		baby.setBabyBirth("1994-04-14");
		String a = "1994-04-14";
		String b = "1994-05-15";
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime dt1 = formatter.parseDateTime(a);
		DateTime dt2 = formatter.parseDateTime(b);
		System.out.println(dt1);
		Days daysBetween = Days.daysBetween(dt1.toLocalDate(), dt2.toLocalDate());
		System.out.println(daysBetween.getDays());
//		DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
//		Date a1 = sdFormat.parse(a);
//		Date b1 = sdFormat.parse(b);
//		System.out.println(a1);
//		System.out.println(b1);
//		int days = Date.daysBetween(a1, b1).getDays();
//		System.out.println(result);
		
	}
}
