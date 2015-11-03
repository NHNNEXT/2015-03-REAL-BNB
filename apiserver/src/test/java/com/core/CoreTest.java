package com.core;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

public class CoreTest {
	class Hello{
		
	}
	@Test
	public void test() {
		Hello hi1 = new Hello();
		Hello hi2 = new Hello();
		Hello hi3 = new Hello();
		
		Set<Hello> temp = new HashSet<Hello>();
		temp.add(hi1);
		temp.add(hi2);
		temp.add(hi3);
		System.out.println(temp.size());
		assertEquals(temp.size(),3);
		temp.add(hi3);
		assertEquals(temp.size(),3);
	}
	
	@Test
	public void testForMultipart() throws Exception {
//		FileInputStream inputFile = new FileInputStream("/Users/erin/git/2015-03-REAL-BEAUTIES-NB/apiserver/src/main/resources/static/asdf.jpeg");			
		FileInputStream inputFile = new FileInputStream("src/main/resources/static/asdf.jpeg");
		
		MockMultipartFile file = new MockMultipartFile("file", "NameOfTheFile", "multipart/form-data", inputFile);
		assertEquals(file.getName(), "file");
		assertEquals(file.getOriginalFilename(), "NameOfTheFile");
		assertEquals(file.getContentType(), "multipart/form-data");
		assertEquals(file.getSize(), 5262);
	}

}
