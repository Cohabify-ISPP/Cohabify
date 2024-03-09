package org.ispp4.cohabify.storage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Profile("prod")
public class GoogleStorageService implements StorageService {
	
    private Storage storage;
    private String bucketName = "cohabify.appspot.com";

    @Value("${static.files.host}")
    private String host;
    
    public GoogleStorageService() throws IOException {
    	
    	InputStream credentialsStream = getClass().getClassLoader().getResourceAsStream("cohabify-credentials.json");
        if (credentialsStream == null) {
            log.error("Google bucket credentials file not found in classpath.");
        } else {
        	GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
                    .createScoped(Collections.singleton("https://www.googleapis.com/auth/devstorage.full_control"));
            storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            log.info("Google bucket storage access credentials loaded succesfully.");
        }
        
    }

    // Method to save an image and return its URL
    public String saveImage(String objectName, MultipartFile file) throws IOException {
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setAcl(Collections.singletonList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER)))
                .build();
        storage.create(blobInfo, file.getBytes());
        return this.host + bucketName + "/" + objectName;
    }

}
