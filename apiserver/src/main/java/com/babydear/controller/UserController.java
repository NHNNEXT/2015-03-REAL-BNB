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

	@Autowired TagService tagService;
	@Autowired ImgService imgService;
	@Autowired MailService mailService;
	@Autowired AuthService authService;
	
	@Autowired CardRepository cardRepo;
	@Autowired UserRepository userRepo;
	@Autowired BabyRepository babyRepo;
	@Autowired FamilyRepository familyRepo;
	
	@RequestMapping("/haha")
	public String userss(String token) throws NotGoodExtention {
		throw new NotGoodExtention(token);
	}
	
	// user들을 전부 보여준다 : 
	@RequestMapping("/api/user")
	public List<User> user(String token) {
		if(token.equals("asdf1234")) return userRepo.findAll();
		else return null;
	}
	
	// 입력한 이메일으로 회원가입 가능한지
	@RequestMapping("/api/user/isNewEmail")
	public ResponseDTO isNewEmail(String email){
		if(email == null || email.isEmpty()) return new ResponseDTO(false, "이메일을 입력해 주세요");
		if(userRepo.findByEmail(email) != null) return new ResponseDTO(false, "이미 존재하는 이메일 입니다.");
		return new ResponseDTO(true, null);
	}
	
	// 회원가입 폼을 받아 User을 create 합니다.
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
		try {
			User user = authService.getUser(token);
			return babyRepo.findByFId(user.getFId());
		} catch (NotToken e) {
			return babyRepo.findAll();
		}
//		return babyRepo.findAll();
//		return babyRepo.findByBabyName("꽁꽁이");
	}
	
	
	@RequestMapping(value = "/api/user/login", consumes ="application/json")
	public AuthDTO login(@RequestBody Map<String, Object> userDTO) {
		logger.info("/api/user/login:{}", userDTO);
		User user = userRepo.findByEmail((String)userDTO.get("email"));
		if (user == null) return new AuthDTO(null, "이메일 주소를 다시 입력해 주세요");
		Boolean result = user.checkPW((String)userDTO.get("password"));
		if (result) {
			return new AuthDTO(authService.setUser(user.getUId()), new Date().toString());
		} else {
			return new AuthDTO(null, "비밀번호가 잘못 되었습니다");
		}
	}
	@RequestMapping(value = "/api/user/signup/fb_token/image")
	public ResponseDTO signupFb(String token, MultipartFile image) {
		try {
			User user = authService.getUser(token);
			user.setUserImg(imgService.processImgUser(image));
			userRepo.save(user);
			return new ResponseDTO(true, null, user);
		} catch (IllegalStateException | IOException e) {
			return new ResponseDTO(false, e.getMessage());
		} catch (NotGoodExtention e) {
			return new ResponseDTO(false, "이미지 형식이 잘못 되었습니다.");
		} catch (StringIndexOutOfBoundsException e) {
			return new ResponseDTO(false, "이미지 형식이 없네요");
		} catch (NotToken e) {
			return new ResponseDTO(false, e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/api/user/signup/fb_token", consumes ="application/json")
	public AuthDTO signupFb(@RequestBody Map<String, Object> userDTO) {
		logger.info("/api/user/signup/fb_token:{}", userDTO);
		User user = userRepo.findByEmail((String)userDTO.get("email"));
		if(user == null || user.getUId() == null) {
			user = new User();
			user.setEmail((String)userDTO.get("email"));
			user.setUserRole((String)userDTO.get("role"));
			user.setPassword((String)userDTO.get("fb_token"));
			user.setUserImg("/imgs/sample/user.jpeg");
			userRepo.save(user);
			System.out.println("회원가입"+user);
			return new AuthDTO(authService.setUser(user.getUId()), "성공");
			
		}else{
			System.out.println("바로 로그인 가능"+user);
			return new AuthDTO(authService.setUser(user.getUId()), "이미 가입 되었습니다.");
		}
	}
	
	@RequestMapping(value = "/api/user/login/fb", consumes ="application/json")
	public AuthDTO loginFb(@RequestBody Map<String, Object> userDTO) {
		logger.info("/api/user/login:{}", userDTO);
		User user = userRepo.findByEmail((String)userDTO.get("email"));
		
		return new AuthDTO("asdf1234", "good");
//		userRepo.findByEmail((String)userDTO.get("role"));
//		if (user == null) return new AuthDTO(null, "이메일 주소를 다시 입력해 주세요");
//		Boolean result = user.checkPW((String)userDTO.get("password"));
//		if (result) {
//			return new AuthDTO(authService.setUser(user.getUId()), new Date().toString());
//		} else {
//			return new AuthDTO(null, "비밀번호가 잘못 되었습니다");
//		}
	}

	@RequestMapping("/api/user/token")
	public ResponseDTO token(String token){
		try {
			User user = authService.getUser(token);
			authService.setUser(user.getUId(), token, 30);
			return new ResponseDTO(true, token);
		} catch (NotToken e) {
			return new ResponseDTO(false, "로그인이 필요한 사용자 입니다");
		}
		
	}

}
