package com.babydear.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ResponseDTO {
	private Boolean state;
	private String error;
}
