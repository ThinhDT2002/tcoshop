package com.tcoshop.api;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tcoshop.service.ImageService;

@RestController
@CrossOrigin("*")
public class ImageAPI {
	@Autowired
	ImageService imageService;
	
	@PostMapping("/api/image/save/{folder}")
	public JsonNode display(@RequestParam("file") MultipartFile multipartfile,
			@PathVariable("folder") String folder) {
		File saveFile = imageService.display(multipartfile, folder);
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("name", saveFile.getName());
		node.put("size", saveFile.length());
		return node;
	}
	
	@PostMapping("/api/image/{folder}")
	public String saveImage(@RequestParam("file") MultipartFile multipartFile, 
			@PathVariable("folder") String folder) {
		String imageName = imageService.save(multipartFile, folder);
		return imageName;
	}
}
