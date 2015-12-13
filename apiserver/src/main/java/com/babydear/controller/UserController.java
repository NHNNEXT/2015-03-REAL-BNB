package com.babydear.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.persistence.NonUniqueResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.babydear.dto.AuthDTO;
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
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired RedisTemplate<String, Long> template;

	@Autowired TagService tagService;
	@Autowired ImgService imgService;
	@Autowired MailService mailService;
	@Autowired AuthService authService;
	@Autowired CardRepository cardRepo;
	@Autowired UserRepository userRepo;
	@Autowired BabyRepository babyRepo;
	@Autowired FamilyRepository familyRepo;

	@RequestMapping("/api/user")
	public List<User> user() {
		return userRepo.findAll();
	}

	@RequestMapping("/api/user/find")
	public User user(UserDTO userDTO) {
		if (userDTO.getEmail() != null) return userRepo.findByEmail(userDTO.getEmail());
		if (userDTO.getUId() != null) return userRepo.findOne(userDTO.getUId());
		return null;
	}

	@RequestMapping("/api/user/create")
	public AuthDTO create(UserDTO userDTO, MultipartFile image) {
		if (userRepo.findByEmail(userDTO.getEmail()) != null) return new AuthDTO(null, "이메일이 이미 존재 합니다");
		try {
			userDTO.setUserImg(imgService.processImgUser(image));
		} catch (IllegalStateException e) {
			return new AuthDTO(null, "이미지가 너무 크거나 잘못되었습니다.");
		} catch (IOException e) {
			return new AuthDTO(null, "이미지가 너무 크거나 잘못되었습니다.");
		} catch (NotGoodExtention e) {
			return new AuthDTO(null, "이미지 형식이 잘못 되었습니다.");
		}
		User user = new User(userDTO);
		user = userRepo.save(user);
		mailService.sendSignUpMail();
		return new AuthDTO(authService.setUser(user.getUId()), new Date().toString());
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

	@RequestMapping("/api/user/baby/create")
	public ResponseDTO createBaby(String token, Baby baby, MultipartFile image) {
		if(baby.getBabyGender() == null) return new ResponseDTO(false, "아기 정보를 제대로 입력해 주세요: 성별입력 않되어 있어요");
		if(baby.getBabyGender().equals(Baby.Gender.UNDEFINED)) return new ResponseDTO(true, null);
		try {
			baby.setBabyImg(imgService.processImgBaby(image));
		} catch (IllegalStateException | IOException e) {
			return new ResponseDTO(false, "이미지가 너무 크거나 잘못되었습니다.");
		} catch (NotGoodExtention e) {
			return new ResponseDTO(false, "이미지 형식이 잘못 되었습니다.");
		} catch (StringIndexOutOfBoundsException e) {
			return new ResponseDTO(false, "이미지 형식이 없네요");
		}
//		baby.setFId(user.getFId());
		logger.info("baby :{}", baby);
		babyRepo.save(baby);
		return new ResponseDTO(true, null, baby);
	}
	
	@RequestMapping("/api/user/baby")
	public List<Baby> findBaby(String token) {
		if(token == null || token.isEmpty()) return null;
//		try {
//			User user = authService.getUser(token);
//			return babyRepo.findByFId(user.getFId());
//		} catch (NotToken e) {
//			return null;
//		}
		return babyRepo.findAll();
//		return babyRepo.findByBabyName("꽁꽁이");
	}
	
	
	@RequestMapping("/api/user/login")
	public AuthDTO login(UserDTO userDTO) {
		logger.info("/api/user/login:{}", userDTO);
		System.out.println(userDTO);
		User user = userRepo.findByEmail(userDTO.getEmail());
		if (user == null) return new AuthDTO(null, "이메일 주소를 다시 입력해 주세요");
		Boolean result = user.checkPW(userDTO.getPassword());
		if (result) {
			return new AuthDTO(authService.setUser(user.getUId()), new Date().toString());
		} else {
			return new AuthDTO(null, "비밀번호가 잘못 되었습니다");
		}
	}
	
	@RequestMapping("/api/user/token")
	public ResponseDTO token(String token){
		User user = null;
		try {
			user = authService.getUser(token);
		} catch (NotToken e) {
			return new ResponseDTO(false, "로그인이 필요한 사용자 입니다");
		}
		return new ResponseDTO(true, token);
	}

}
