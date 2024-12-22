package com.justcode.vehicleSharing.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileStorageService {

    @Value("${application.supabase.url}")
    private String supabaseUrl;

    @Value("${application.supabase.anon-key}")
    private String supabaseAnonKey;

    @Value("${application.supabase.bucket-name}")
    private String bucketName;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Uploads a file to Supabase Storage.
     *
     * @param file   The file to be uploaded.
     * @param userId The ID of the user uploading the file.
     * @return The file path if successful.
     */
    public String save(MultipartFile file, Integer userId) {

        return  "";
    }
}
