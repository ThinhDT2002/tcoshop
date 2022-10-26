package com.tcoshop.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	File display(MultipartFile multipartFile, String folder);
	String save(MultipartFile multipartFile, String folder);
}
