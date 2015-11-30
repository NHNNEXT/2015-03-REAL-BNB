package com.babydear.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@WebFilter(urlPatterns = { "/api/card/*" })
public class AuthFilter implements Filter {
	@Autowired
	RedisTemplate<String, Long> template;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("hello filter");
		String token = request.getParameter("token");
		System.out.println(token);
		if(token != null){
			ValueOperations<String, Long> ops = template.opsForValue();
			Long uId = ops.get(token);
			System.out.println(uId);
			request.setAttribute("uId", uId);
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}
}