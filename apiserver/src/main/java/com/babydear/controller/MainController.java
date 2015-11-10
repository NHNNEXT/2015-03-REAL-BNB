package com.babydear.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.babydear.dto.UserDTO;
import com.babydear.model.User;

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

	@RequestMapping("/user/create")
	public String create(UserDTO user) {
		System.out.println(user);
		return "redirect:/";
	}

	@RequestMapping("/user/login")
	public String login(User user) {
		System.out.println(user);
		return "redirect:/";
	}
}
