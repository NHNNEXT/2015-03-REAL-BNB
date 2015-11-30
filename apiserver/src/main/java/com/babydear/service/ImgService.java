package com.babydear.service;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImgService {

	public String processImg(MultipartFile image) {
		if(image.isEmpty()){
			System.out.println("is empty");
		}
		
		System.out.println(image.getName());

//        String fileName = getName(image);

//        File fileStorePath = new File(FILE_STORAGE_DIRECTORY + fileName);
		File fileStorePath = new File("/Users/erin/dummy/"+image.getOriginalFilename());
        try {
			image.transferTo(fileStorePath);
		} catch (IllegalStateException e) {
			System.out.println(e);
//			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e);
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
//        try {
//            file.transferTo(fileStorePath);
//        } catch (IllegalStateException | IOException e) {
//            return new CommonJsonResponse(ResponseCode.FileUpload.ERROR_OCCURED_WHILE_UPLOADING_ATTACHMENT);
//        }
		return null;
	}

}
