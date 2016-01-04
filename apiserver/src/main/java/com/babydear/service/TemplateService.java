package com.babydear.service;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.imageio.ImageIO;
//import javax.vecmath.Vector2d;

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
import com.babydear.model.Card;
import com.babydear.repository.BabyRepository;

@Service
@PropertySource("classpath:config.properties")
public class TemplateService {
	@Value("${FILE_UPLOAD_PATH}")
	private String FILE_STORAGE_DIRECTORY;
	private static final int IMG_WIDTH = 100;
	private static final int IMG_HEIGHT = 100;
	private static final Font f = new Font("Dialog", Font.PLAIN, 12);
//	private static final List<Vector2d> cordinates = Arrays.asList(
//			new Vector2d(100,200),
//			new Vector2d(200,300),
//			new Vector2d(300,200),
//			new Vector2d(100,200),
//			new Vector2d(100,200),
//			new Vector2d(100,200));
//	
//	

	public void setFILE_STORAGE_DIRECTORY(String fILE_STORAGE_DIRECTORY) {
		FILE_STORAGE_DIRECTORY = fILE_STORAGE_DIRECTORY;
	}
	public void setCards(List<Card> cards) throws IOException {
		BufferedImage originTemplate = ImageIO.read(new File(FILE_STORAGE_DIRECTORY + "/imgs/sample/phoster_template_sm.jpg"));
		int type = originTemplate.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originTemplate.getType();
		for(int i=0; i<cards.size(); i++){
//			 setCard(cards.get(i),cordinates.get(i), originTemplate);
		}
	}

//	private void setCard(Card card, Vector2d vector2d, BufferedImage originTemplate) throws IOException {
//		String imgUrl = card.getCardImg();
//		System.out.println(imgUrl);
//		BufferedImage photo = ImageIO.read(new File(FILE_STORAGE_DIRECTORY + imgUrl));
//		int type = originTemplate.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originTemplate.getType();
//		photo = resizeImage(photo, type);
//		
//		Graphics2D g = originTemplate.createGraphics();
//			g.setFont(f);
////			g.drawImage(photo,vector2d.x, vector2d.y, IMG_WIDTH, IMG_HEIGHT, null);
////			g.dr
//			g.drawString("우리아기 무럭무럭 자라렴", 200, 200);
//		g.dispose();
//		
//		
//	}
	public void processTags(List<Long> cIds) throws IOException {
		BufferedImage originTemplate = ImageIO
				.read(new File(FILE_STORAGE_DIRECTORY + "imgs/sample/phoster_template_sm.jpg"));
		int type = originTemplate.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originTemplate.getType();
		BufferedImage originalImage = resizeImage(originTemplate, type, 738, 1042);

		BufferedImage photo1 = ImageIO.read(new File(FILE_STORAGE_DIRECTORY + "imgs/dummy/photo1.jpg"));
		BufferedImage resizeImageJpg = resizeImage(photo1, type);
		BufferedImage putImageJpg = addImage(originalImage, Arrays.asList(photo1));
		ImageIO.write(putImageJpg, "jpg", new File(FILE_STORAGE_DIRECTORY + "imgs/sample/asdf.jpg"));
		// ImageIO.write(resizeImageJpg, "jpg", new File(FILE_STORAGE_DIRECTORY
		// + "imgs/sample/asdf.jpg"));
		// ImageIO.write(originalImage, "jpg", new File(FILE_STORAGE_DIRECTORY +
		// "imgs/sample/asdf.jpg"));

	}

	private BufferedImage addImage(BufferedImage originalImage, List<BufferedImage> photos) {
		// BufferedImage resizedImage = new
		// BufferedImage(originalImage.getWidth(), originalImage.getHeight(),
		// originalImage.getType());
		BufferedImage resizedImage = originalImage;
		Graphics2D g = resizedImage.createGraphics();
		// g.drawImage(originalImage, 0, 0, originalImage.getWidth(),
		// originalImage.getHeight(), null);
		for (int i = 0; i < photos.size(); i++) {
			g.setFont(f);
			g.drawImage(photos.get(i), 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
			g.drawString("우리아기 무럭무럭 자라렴", 200, 200);
		}
		g.dispose();
		return resizedImage;
	}

	private BufferedImage resizeImage(BufferedImage originalImage, int type, int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}

	private BufferedImage resizeImage(BufferedImage originalImage, int type) {
		return resizeImage(originalImage, type, IMG_HEIGHT, IMG_WIDTH);
	}



}
