package com.babydear;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ApplicationConfig {
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**")
//				.allowedOrigins("http://dev.balbum.net", "http://localhost:8080", "http://"))
				.allowedMethods("GET", "POST", "PUT", "DELETE")
				.allowCredentials(false).maxAge(3600);
			}
		};
	}

	@Bean
	public RedisTemplate<String, Long> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Long> template = new RedisTemplate<String, Long>();
		template.setConnectionFactory(connectionFactory);
		return template;
	}
}
