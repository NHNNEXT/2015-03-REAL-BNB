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

public class Baby {
	public enum Sex {
		GIRL,
		BOY,
		UNDEFIEND
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bId;
	
//	@JoinColumn(name = "oid", referencedColumnName="oid")
	@ManyToOne
	private Family fid;
	
	private String name;
	
//	@Enumerated(EnumType.STRING)
	private Sex sex;
	
	@Temporal(TemporalType.DATE)
	private Date birth;

	public String getImg(){
		return "/babyImg/b"+bId+".jpg";
	}
}
