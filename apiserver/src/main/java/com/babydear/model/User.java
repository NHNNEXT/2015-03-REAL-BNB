package com.babydear.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class User {
	public enum SignIn {
		NAVER,
		KAKAO,
		FACEBOOK,
		NORMAL
	}
	
	public enum State {
		MANAGER,
		NORMAL,
		WITHDRAW,
		PRISON
	}
	
	public enum Sex {
		WOMAN,
		MAN
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long uId;
	
	@ManyToOne
	private Family fid;
	
	private String userName;
	private String userImg;
	
	private String email;
	private String password;
	private Sex sex;
	private State state;
	private SignIn signIn;
}
