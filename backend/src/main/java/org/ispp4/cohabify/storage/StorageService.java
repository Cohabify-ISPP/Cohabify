package org.ispp4.cohabify.storage;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    // Method to save an image and return its URL
    public String saveImage(String objectName, MultipartFile file) throws IOException;

}
