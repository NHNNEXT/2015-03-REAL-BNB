package com.babydear.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class CardDTO {

	private String content;
	private String modifiedDate;
	private List<MultipartFile> files;

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
}
