package com.babydear.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.babydear.dto.AuthDTO;
import com.babydear.dto.ResponseDTO;
import com.babydear.dto.UserDTO;
import com.babydear.model.Baby;
import com.babydear.model.Family;
import com.babydear.model.User;
import com.babydear.repository.BabyRepository;
import com.babydear.repository.CardRepository;
import com.babydear.repository.FamilyRepository;
import com.babydear.repository.UserRepository;
import com.babydear.service.ImgService;
import com.babydear.service.MailService;
import com.babydear.service.TagService;

@RestController
// @RequestMapping(value = "/api/card")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	RedisTemplate<String, Long> template;

	@Autowired
	TagService tagService;

	@Autowired
	ImgService imgService;

	@Autowired
	MailService mailService;

	@Autowired
	CardRepository cardRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	BabyRepository babyRepo;
	@Autowired
	FamilyRepository familyRepo;

	@RequestMapping("/api/user")
	public List<User> user() {
		return userRepo.findAll();
	}

	@RequestMapping("/api/user/find")
	public User user(UserDTO userDTO) {
		System.out.println(userDTO);
		if (userDTO.getEmail() != null) return userRepo.findByEmail(userDTO.getEmail());	
		if (userDTO.getUId() != null) return userRepo.findOne(userDTO.getUId());
		
		return null;
	}

	@RequestMapping("/api/user/create")
	public AuthDTO create(UserDTO userDTO, MultipartFile image) {
		logger.info("/api/user/create:{}", userDTO);
		try {
			userDTO.setUserImg(imgService.processImg(image));
		} catch (IllegalStateException e) {
			return new AuthDTO(null, "이미지가 잘못되었습니다.");
		} catch (IOException e) {
			return new AuthDTO(null, "이미지가 잘못되었습니다.");
		}
		User user = new User(userDTO);
		if (userRepo.findByEmail(user.getEmail()) != null) {
			return new AuthDTO(null, "이메일이 이미 존재 합니다");
		}
		user = userRepo.save(user);
		// List<Baby> babies = userDTO.getBabies();
		// babyRepo.save(babies);
		mailService.sendSignUpMail();
		return new AuthDTO(setToken(user.getUId()), new Date().toString());
	}

	@RequestMapping("/api/user/family/find")
	public ResponseDTO findFamily(Long fId, User user) {
		if (fId == null)
			return new ResponseDTO(false, "패밀리 아이디를 입력해 주세요 ");
		if (!familyRepo.exists(fId))
			return new ResponseDTO(false, "없는 패밀리 아이디 입니다.");
		return new ResponseDTO(true, null);
	}

	@RequestMapping("/api/user/family/create")
	public ResponseDTO createFamily(User user) {
		Family family = familyRepo.save(new Family());
		user.setFId(family.getFId());
		userRepo.save(user);
		return new ResponseDTO(true, null);
	}
	
	@RequestMapping("/api/user/baby/create")
	public ResponseDTO createBaby(User user, List<Baby> babies) {
//		if(babies.iterator())
		logger.debug("{babies}",babies);
		for (Baby baby : babies) {
			baby.setFid(user.getFId());
		}
		babyRepo.save(babies);
//		List<Baby> result = babyRepo.save(babies);
		return new ResponseDTO(true, null);
	}

	@RequestMapping("/api/user/login")
	public AuthDTO login(UserDTO userDTO) {
		logger.info("/api/user/login:{}", userDTO);
		User user = userRepo.findByEmail(userDTO.getEmail());
		if (user == null)
			return new AuthDTO(null, "이메일 주소를 다시 입력해 주세요");
		Boolean result = user.checkPW(userDTO.getPassword());
		if (result) {
			return new AuthDTO(setToken(user.getUId()), new Date().toString());
		} else {
			return new AuthDTO(null, "비밀번호가 잘못 되었습니다");
		}
	}

	private String setToken(Long uId) {
		ValueOperations<String, Long> ops = template.opsForValue();
		String token = UUID.randomUUID().toString();
		while (template.hasKey(token)) {
			token = UUID.randomUUID().toString();
		}

		ops.set(token, uId, 1, TimeUnit.MINUTES);
		System.out.println("your token is : " + token);
		return token;

	}

}
