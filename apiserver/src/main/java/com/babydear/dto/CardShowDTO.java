package com.babydear.dto;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CardShowDTO {
	private Long cId = new Long(1);
	private String content ="이쁜 우리 아기 걸음마 시작! 축하해 뿅뿅아";
	private String modifiedDate ="2010-10-10";
	private String imgUrl = "img/images.jpeg";
	private List<Long> babies = Arrays.asList(new Long(1),new Long(1),new Long(1));
}
