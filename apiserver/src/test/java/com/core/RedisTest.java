package com.core;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.babydear.ApiserverApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApiserverApplication.class)
@WebAppConfiguration
public class RedisTest {

	@Autowired
	RedisTemplate<String, Long> template;
	
	@Test
	public void redis() throws Exception {
		assertNotNull(template);
		
	}
	@Test
	public void redisSetGet() throws Exception {
		ValueOperations<String, Long> ops = template.opsForValue();
//		ops.set(key, value, timeout, unit);
		System.out.println(template.hasKey("asdf"));
		ops.set("asdf", new Long(1234), 13, TimeUnit.SECONDS);
		System.out.println(ops.get("asdf"));
		System.out.println(template.hasKey("asdf"));
//		ops.set("asdf", new Long(1234));
		
	}
	@Test
	public void UUID() throws Exception {
		ValueOperations<String, Long> ops = template.opsForValue();
//		ops.set(key, value, timeout, unit);
		String token = java.util.UUID.randomUUID().toString();
		System.out.println(template.hasKey(token));
		ops.set("asdf", new Long(1234), 1, TimeUnit.SECONDS);
		System.out.println(ops.get("asdf"));
		System.out.println(template.hasKey("asdf"));
	}
}
