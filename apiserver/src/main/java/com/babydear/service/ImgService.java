package com.babydear.service;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@PropertySource("classpath:config.properties")
public class ImgService {
    @Value("${FILE_UPLOAD_PATH}")
    private String FILE_STORAGE_DIRECTORY;
    
	public String processImg(MultipartFile image) throws IllegalStateException, IOException {
		if(image == null || image.isEmpty()){
			System.out.println("is empty");
			return "/img/user/sample.jpeg";
		}
		
		File fileStorePath = new File(FILE_STORAGE_DIRECTORY+image.getOriginalFilename());
		image.transferTo(fileStorePath);
		return "/img/user/"+image.getOriginalFilename();
	}

}
