package com.babydear.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Baby {
	public enum Gender {
		GIRL,
		BOY,
		PREGNANCY,
		UNDEFINED
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bid;
	
//	@JoinColumn(name = "oid", referencedColumnName="oid")
//	@ManyToOne
	private Long fid;
	
	private String babyName;
	
//	@Enumerated(EnumType.STRING)
	private Gender babyGender;
	private String babyBirth;
	private String babyImg;

}
