package org.ispp4.cohabify.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Profile("!prod")
public class LocalStorageService implements StorageService {

    private String static_folder_uri;
    private String images_folder_path;

    @Value("${static.files.host}")
    private String host;

    public LocalStorageService() {
    	this.static_folder_uri = "/uploads/saved-images/user-profile-pictures/";
		this.images_folder_path = "src/main/resources/static/uploads/saved-images/user-profile-pictures/";
        
        log.info("Local storage service loaded.");
    }

    public String saveImage(String objectName, MultipartFile file) throws IOException {
        Path path = Paths.get(this.images_folder_path + objectName);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());
        return host + this.static_folder_uri + objectName;
    }

}
