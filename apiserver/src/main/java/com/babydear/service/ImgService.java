package com.babydear.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.babydear.exception.NotGoodExtention;

@Service
@PropertySource("classpath:config.properties")
public class ImgService {
    @Value("${FILE_UPLOAD_PATH}")
    private String FILE_STORAGE_DIRECTORY;
    
    public String processImgCard(MultipartFile image) throws IllegalStateException, IOException, NotGoodExtention{
    	return processImg(image, "/imgs/card/", "/imgs/sample/card.jpeg");
    }
    public String processImgUser(MultipartFile image) throws IllegalStateException, IOException, NotGoodExtention{
    	return processImg(image, "/imgs/user/", "/imgs/sample/user.jpeg");
    }
    public String processImgBaby(MultipartFile image) throws IllegalStateException, IOException, NotGoodExtention{
    	return processImg(image, "/imgs/baby/", "/imgs/sample/baby.jpeg");
    }
    
	public String processImg(MultipartFile image, String localUrl, String sample) throws IllegalStateException, IOException, NotGoodExtention {
		if(image == null || image.isEmpty()){
			return sample;
		}
		String extention = extractFileExtention(image.getOriginalFilename());
		if(!extention.equals("jpeg") && !extention.equals("jpg") && !extention.equals("gif") && extention.equals("png")) 
			throw new NotGoodExtention("이런 형식은 지원하지 않습니다 "+extention);
		
		String name = getName(image);
		
		ensureFileSaveDirectoryExist(FILE_STORAGE_DIRECTORY+localUrl);
		File fileStorePath = new File(FILE_STORAGE_DIRECTORY+localUrl+name);
		image.transferTo(fileStorePath);
		return localUrl+name;
	}
	
    private String getName(MultipartFile file) {
        return System.currentTimeMillis() + UUID.randomUUID().toString().replace("-", "") 
        		+ extractFileExtention(file.getOriginalFilename());
    }

    private void ensureFileSaveDirectoryExist(String fileSaveDirectoryPath) {
        File fileSaveDirectory = new File(fileSaveDirectoryPath);
        if(!fileSaveDirectory.exists()) fileSaveDirectory.mkdirs();
    }

    private String extractFileExtention(String fileName) throws StringIndexOutOfBoundsException{
        int lastPeriod = fileName.lastIndexOf(".");
        return fileName.substring(lastPeriod, fileName.length());
    }

}
