package com.babydear.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

public class Regex {

	@Test
	public void test() {
		String a = "1994-04-24";
		String b = "April 4 14";
		System.out.println(a.matches("....-..-.."));
	}

}
