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
	private User user;
	public MainShowDTO(User user, List<Baby> babies) {
		this.user = user;
		this.babies = babies;
	}
}
