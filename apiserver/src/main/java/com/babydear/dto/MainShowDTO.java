package com.babydear.dto;

import java.util.List;

import com.babydear.model.Baby;
import com.babydear.model.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
public class MainShowDTO {
	private List<Baby> babies;
	private List<User> families;
	private List<User> readyList;
	private User user;
	
	public MainShowDTO(User user, List<Baby> babies) {
//		for web front;
		this.user = user;
		this.babies = babies;
	}

	public MainShowDTO(List<Baby> babies, List<User> families, List<User> readyList, User user) {
		super();
		this.babies = babies;
		this.families = families;
		this.readyList = readyList;
		this.user = user;
	}
}
