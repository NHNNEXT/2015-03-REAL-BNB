package com.core.set;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Test;

import com.babydear.model.CardImage;

public class SetTest {

	@Test
	public void test() {
		CardImage img1 = new CardImage();
		CardImage img2 = new CardImage();
		CardImage img3 = new CardImage();
		Set a1;
		Set a2;
		
		Set temp = new HashSet();
		temp.add(img1);
		temp.add(img1);
		temp.add(img2);
		temp.add(new CardImage());
		
	}

}
