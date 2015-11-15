package com.babydear.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	@Async
	public void sendSignUpMail() {
		System.out.println("mailsend");
	}
}
