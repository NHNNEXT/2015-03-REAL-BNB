package com.babydear.dto;


import java.util.List;

import com.babydear.model.Baby;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserDTO {
	private Long fId;
	private String email;
	private String password;
	private String role;
	private List<Baby> babies;
}
