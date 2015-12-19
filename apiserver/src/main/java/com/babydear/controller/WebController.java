package com.babydear.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.babydear.dto.AuthDTO;
import com.babydear.dto.MainShowDTO;
import com.babydear.dto.ResponseDTO;
import com.babydear.dto.UserDTO;
import com.babydear.exception.NotGoodExtention;
import com.babydear.exception.NotToken;
import com.babydear.model.Baby;
import com.babydear.model.Family;
import com.babydear.model.User;
import com.babydear.repository.BabyRepository;
import com.babydear.repository.CardRepository;
import com.babydear.repository.FamilyRepository;
import com.babydear.repository.UserRepository;
import com.babydear.service.AuthService;
import com.babydear.service.ImgService;
import com.babydear.service.MailService;
import com.babydear.service.TagService;

@RestController
// @RequestMapping(value = "/api/card")
public class WebController {
	private static final Logger logger = LoggerFactory.getLogger(WebController.class);

	@Autowired TagService tagService;
	@Autowired ImgService imgService;
	@Autowired MailService mailService;
	@Autowired AuthService authService;
	
	@Autowired CardRepository cardRepo;
	@Autowired UserRepository userRepo;
	@Autowired BabyRepository babyRepo;
	@Autowired FamilyRepository familyRepo;
	
	@RequestMapping("/api/main")
	public ResponseDTO userss(String token) {
		try {
			User user = authService.getUser(token);
			List<Baby> babies = babyRepo.findByFId(user.getFId());
			return new ResponseDTO(true, "", new MainShowDTO(user, babies));
			
		} catch (NotToken e) {
			return new ResponseDTO(false, "유효 하지 않은 토큰 입니다.");
		}
	}
	
}
