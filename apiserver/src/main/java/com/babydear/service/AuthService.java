package com.babydear.service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.babydear.exception.NotToken;
import com.babydear.model.User;
import com.babydear.repository.UserRepository;

@Service
public class AuthService {
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
	@Autowired RedisTemplate<String, Long> template;
	@Autowired UserRepository userRepo;

	public User getUser(String token) throws NotToken {
		if(token == null || token.isEmpty()) throw new NotToken("토큰을 넣어 주세요");
		ValueOperations<String, Long> ops = template.opsForValue();
		Long uId = ops.get(token);
		if (uId == null) throw new NotToken("토큰이 유효하지 않습니다.(1) token:"+token);
		User user = getUser(uId);
		if(user == null) throw new NotToken("토큰이 유효하지 않습니다.(2) token:"+token);
		return user;
	}

	public User getUser(Long uId) {
		return userRepo.findOne(uId);
	}

	public String setUser(Long uId) {
		ValueOperations<String, Long> ops = template.opsForValue();
		String token = UUID.randomUUID().toString();
		while (template.hasKey(token)) {
			token = UUID.randomUUID().toString();
		}
		ops.set(token, uId, 30, TimeUnit.DAYS);
		return token;

	}

	public String setUser(Long uId, String token) {
		ValueOperations<String, Long> ops = template.opsForValue();
		ops.set(token, uId);
		logger.info(token);
		return token;
	}

}
