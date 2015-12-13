package com.babydear.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ResponseDTO {
	private boolean state;
	private String error;
	private Object res;
	
	public ResponseDTO(){}
	public ResponseDTO(Boolean state, String error) {
		super();
		this.state = state;
		this.error = error;
	}
	public ResponseDTO(Boolean state, Object error, Object res) {
		this.res = res;
	}
}
