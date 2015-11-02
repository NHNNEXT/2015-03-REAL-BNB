package com.babydear.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.babydear.model.CardImage;

public class CardDTO {

	private Long cId;
	private String content;
	private String modifiedDate;
	private List<MultipartFile> files;

	
	public Long getcId() {
		return cId;
	}

	public void setcId(Long cId) {
		this.cId = cId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
	

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override
	public String toString() {
		return "CardDto [ content=" + content + ", modifiedDate=" + modifiedDate + ", files=" + files.size() + "]";
	}

	public int getImageSize() {
		return files.size();
	}

	public Collection<? extends CardImage> getImages() {
		// TODO Auto-generated method stub
		return null;
	}
}
