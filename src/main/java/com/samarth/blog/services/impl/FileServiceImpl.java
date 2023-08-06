package com.samarth.blog.services.impl;

import com.samarth.blog.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //File name
        String name = file.getOriginalFilename();

        //File FullPath
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));

        String filePath = path + File.separator + fileName;

        //create file or folder
        File f = new File(path);
        if (!f.exists()) {

            f.mkdir();
        }

        //file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;
    }

    @Override
    public InputStream getResource(String path, String pathName) throws FileNotFoundException {

        String fullPath = path + File.separator + pathName;
        return new FileInputStream(fullPath);
    }
}
