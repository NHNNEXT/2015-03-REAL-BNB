package com.babydear.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
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
}
