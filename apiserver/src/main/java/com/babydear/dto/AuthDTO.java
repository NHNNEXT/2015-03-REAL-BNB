package com.babydear.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthDTO {
	public AuthDTO(String token, String message) {
		this.token = token;
		this.message = message;
	}
	private String token;
	private String message;
}
