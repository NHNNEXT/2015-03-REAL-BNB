package com.babydear.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.babydear.controller.UserController;
import com.babydear.exception.NotGoodExtention;
import com.babydear.model.Baby;
import com.babydear.repository.BabyRepository;

@Service
@PropertySource("classpath:config.properties")
public class TemplateService {
	 @Value("${FILE_UPLOAD_PATH}")
	 private String FILE_STORAGE_DIRECTORY;

	public void setFILE_STORAGE_DIRECTORY(String fILE_STORAGE_DIRECTORY) {
		FILE_STORAGE_DIRECTORY = fILE_STORAGE_DIRECTORY;
	}


	public void processTags(List<Long> cIds) throws IOException {
		BufferedImage originalImage = ImageIO.read(new File(FILE_STORAGE_DIRECTORY+"imgs/sample/phoster_template.jpg"));
		int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		BufferedImage photo1 = ImageIO.read(new File(FILE_STORAGE_DIRECTORY+"imgs/dummy/photo1.jpg"));
		BufferedImage resizeImageJpg = resizeImage(originalImage, type);
		ImageIO.write(originalImage, "jpg", new File(FILE_STORAGE_DIRECTORY+"imgs/sample/asdf.jpg")); 
	}
	
	private BufferedImage resizeImage(BufferedImage originalImage, int type) {
		// TODO Auto-generated method stub
		return null;
	}
}
