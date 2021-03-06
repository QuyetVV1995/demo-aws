package com.example.demo.service.impl;

import com.example.demo.exception.StorageException;
import com.example.demo.service.StorageService;
import lombok.var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageServiceImpl implements StorageService {
    @Value("${upload.path}")  //Đọc dữ liệu từ tham số cấu hình upload.path
    private String path;
    @Override
    public void uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file");
        }

        String fileName = file.getOriginalFilename();
        try {
            var is = file.getInputStream();
            Files.copy(is, Paths.get(path + fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            var msg = String.format("Failed to store file %s", fileName);
            throw new StorageException(msg, e);
        }
    }

    @Override
    public void uploadFiles(MultipartFile[] files) {
        for (MultipartFile file : files) {
            uploadFile(file);
        }
    }
}
