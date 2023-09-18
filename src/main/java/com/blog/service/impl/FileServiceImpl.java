package com.blog.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		// get the file name 
		String name = file.getOriginalFilename();
		
		// to distinguish the file when user uplaod file with same name
		// add any random id to the file name
				
		// e.g: 'abc.png' h tb ye convert ho jayega 'randomID.png'.
				
		String randomID = UUID.randomUUID().toString();
		// dot se random wale ke saath add ho jayega
		// uske naam nya filename jis naam se file save karenge
		String fileName1 = randomID.concat(name.substring(name.lastIndexOf("."))); 				
		
		// get full path i.e folder and file name
		String filePath = path + File.separator + fileName1;
		
		// create folder if not created
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		// copy file. path_source, path_target
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return fileName1;
}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path+File.separator+fileName;
		InputStream is = new FileInputStream(fullPath);
		
		// for reading data from database , can write logic here
		return is;
	}

}
