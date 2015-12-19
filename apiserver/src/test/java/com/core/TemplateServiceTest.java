package com.core;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.babydear.service.TemplateService;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = ApiserverApplication.class)
//@WebAppConfiguration
public class TemplateServiceTest {
	
	
	@Test
	public void imgService() throws Exception {
		TemplateService s = new TemplateService();
		s.setFILE_STORAGE_DIRECTORY("/Users/erin/git/2015-03-REAL-BEAUTIES-NB/apiserver/src/main/webapp/");
		s.processTags(Arrays.asList(new Long(1),new Long(1),new Long(1)));
		
	}
}
