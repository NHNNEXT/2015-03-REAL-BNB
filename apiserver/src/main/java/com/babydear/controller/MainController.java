package com.babydear.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.babydear.model.Card;

@Controller
public class MainController {
	
	@RequestMapping("/")
	public String mainpage(){
		return "cardForm.html";
	}

}
