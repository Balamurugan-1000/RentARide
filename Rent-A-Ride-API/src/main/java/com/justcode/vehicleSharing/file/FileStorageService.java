package com.justcode.vehicleSharing.file;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;
import static java.lang.System.currentTimeMillis;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileStorageService {
    @Value("${application.file.upload.photos-output-path}")
    private String fileUploadPath;
    public String saveFile(
            @NonNull  MultipartFile source,
            @NonNull  Integer userId) {

        final String fileUploadSubPath = "users" + separator + userId;

        return uploadFile(source,fileUploadSubPath);


    }
    private String uploadFile(@NonNull MultipartFile source, @NonNull  String fileUploadSubPath) {
        final String finalUploadPath = fileUploadPath + separator + fileUploadSubPath;
        File targetFolder = new File(finalUploadPath);
        if(!targetFolder.exists()){
            boolean folderCreated = targetFolder.mkdirs();
            if(!folderCreated){
                log.warn("Failed to create the target folder");
                return null;
            }
        }
        final String fileExtension = getFileExtension(source.getOriginalFilename());
        String targetFilePath = fileUploadPath + separator + currentTimeMillis()  + "." + fileExtension;
        Path targetPath = Paths.get(targetFilePath);
        try {
            Files.write(targetPath , source.getBytes());
            log.info("File saved to {}", targetFilePath);
            return targetFilePath;
        } catch (IOException e) {
            log.error("File was not saved");
            throw new RuntimeException(e);
        }
    }

    private String getFileExtension(String filename) {
        if(filename == null || filename.isEmpty()){
            return "";
        }
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1){
            return "";
        }
        return filename.substring(lastDotIndex+1).toLowerCase();
    }
}
