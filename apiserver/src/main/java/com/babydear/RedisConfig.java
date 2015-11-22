package com.babydear;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
public class RedisConfig {
	@Bean
	public JedisConnectionFactory connectionFactory(){
		return new JedisConnectionFactory();
	}
}
