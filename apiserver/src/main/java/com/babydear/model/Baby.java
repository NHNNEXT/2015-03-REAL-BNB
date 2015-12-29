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
	private Long bId;
	
//	@JoinColumn(name = "oid", referencedColumnName="oid")
//	@ManyToOne
	private Long fId;
	
	private String babyName;
	
//	@Enumerated(EnumType.STRING)
	private Gender babyGender;
	private String babyBirth;
	private String babyImg;
	
	public Baby(){}
//	public Baby(Integer bId) {
//		this.bId = bId.longValue();
//	}
	public Baby(Long bId) {
		this.bId = bId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bId == null) ? 0 : bId.hashCode());
		return result;
	}
//	for show card from baby filtering 
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Baby other = (Baby) obj;
		if (bId == null) {
			if (other.bId != null) return false;
		} else if (!bId.equals(other.bId)) return false;
		return true;
	}

}
