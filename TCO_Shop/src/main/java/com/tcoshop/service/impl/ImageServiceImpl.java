package com.tcoshop.service.impl;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tcoshop.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService{
	@Autowired
	ServletContext app;
	
	private final String URL = System.getProperty("user.dir") + "/src/main/resources/static/images/";
	
	@Override
	public File display(MultipartFile multipartFile, String folder) {
		File dir = new File(app.getRealPath("/images/" + folder + "/"));
		if(!dir.exists()) {
			dir.mkdirs();
		}
		String name = multipartFile.getOriginalFilename();
		try {
			File saveFile = new File(dir, name);
			multipartFile.transferTo(saveFile);
			return saveFile;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public String save(MultipartFile multipartFile, String folder) {
		if(!multipartFile.isEmpty()) {
			String fileName = multipartFile.getOriginalFilename();
			File file = new File(URL + folder + "/" + fileName);
			if(!file.exists()) {
				file.mkdirs();
			}
			try {
				multipartFile.transferTo(file);
				return fileName;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "default-product.png";
	}
}
