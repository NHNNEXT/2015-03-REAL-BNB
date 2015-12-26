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
public class FamilyController {
	private static final Logger logger = LoggerFactory.getLogger(FamilyController.class);

	@Autowired TagService tagService;
	@Autowired ImgService imgService;
	@Autowired MailService mailService;
	@Autowired AuthService authService;
	
	@Autowired CardRepository cardRepo;
	@Autowired UserRepository userRepo;
	@Autowired BabyRepository babyRepo;
	@Autowired FamilyRepository familyRepo;
	
	// 가족의 이메일을 받아 FID를 넣어 줍니다.
	@RequestMapping("/api/user/family/findFromMail")
	public ResponseDTO findFamily(String email, String token) {
		if(email == null) return new ResponseDTO(false, "이메일을 넣어 주세요");
		if(token == null) return new ResponseDTO(false, "토큰을 넣어 주세요");
		try {
			User user = authService.getUser(token);
			User some = userRepo.findByEmail(email);
			user.setFId(some.getFId());
			user = userRepo.save(user);
			logger.info("true");
			return new ResponseDTO(true, null, user);
		} catch (Exception e) {
			logger.info("false");
			return new ResponseDTO(false, e.getMessage());
		}
	}

	@RequestMapping("/api/user/family/find")
	public ResponseDTO findFamily(Long fId, String token) {
		User user;
		try {
			user = authService.getUser(token);
		} catch (NotToken e) {
			return new ResponseDTO(false, e.getMessage());
		}
		if (user == null) return new ResponseDTO(false, "토큰이 유효한 값이 아닙니다.");
		if (fId == null) return new ResponseDTO(false, "패밀리 아이디를 입력해 주세요 ");
		if (!familyRepo.exists(fId)) return new ResponseDTO(false, "없는 패밀리 아이디 입니다.");
		return new ResponseDTO(true, null);
	}

	@RequestMapping("/api/user/family/create")
	public ResponseDTO createFamily(String token) {
		User user;
		try {
			user = authService.getUser(token);
		} catch (NotToken e) {
			return new ResponseDTO(false, e.getMessage());
		}
		if (user == null) return new ResponseDTO(false, "토큰이 유효한 값이 아닙니다.");
		Family family = new Family();
		family.setUpdateDate(new Date());
		family.setUpdateUId(user.getUId());
		family = familyRepo.save(new Family());
		user.setFId(family.getFId());
		userRepo.save(user);
		return new ResponseDTO(true, null, family);
	}

	@RequestMapping("/api/family")
	public MainShowDTO setting(String token){
		if(token == null) return new MainShowDTO(null, null);
		try {
			User user = authService.getUser(token);
			List<Baby> babies = babyRepo.findByFId(user.getFId());
			List<User> families = userRepo.findByFIdAndIsAccepted(user.getFId(), true);
			List<User> readyList = userRepo.findByFIdAndIsAccepted(user.getFId(), false);
			return new MainShowDTO(babies, families, readyList, user);
		} catch (NotToken e) {
			return new MainShowDTO(null, null);
		}	
	}
	
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
