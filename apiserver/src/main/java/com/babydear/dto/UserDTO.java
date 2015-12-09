package com.babydear.dto;


import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.babydear.model.Baby;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserDTO {
	private Long fId;
	private Long uId;
	@NotEmpty(message="no email")
	private String email;
//	@NotEmpty(message="no password")
	private String password;
	private String userImg;
	private String nickname;
	private String role;
	
	public String getUrl() {
		return "img/asdf1234.jpg";
	}

}
