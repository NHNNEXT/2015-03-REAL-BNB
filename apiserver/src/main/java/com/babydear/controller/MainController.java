package com.babydear.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MainController {

	@RequestMapping("/")
	public String mainpage() {
		return "index.html";
	}

	@RequestMapping("/user")
	public String user() {
		return "userPage.html";
	}
	
	@RequestMapping("/card/{linkUrl}")
	public String card(){
		return "card.html";
	}
}
