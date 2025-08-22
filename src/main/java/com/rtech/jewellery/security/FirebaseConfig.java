package com.rtech.jewellery.security;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initializeFirebase() throws IOException {

        //Resource resource = new ClassPathResource("jewellery.json");

        String firebaseConfigPath = System.getenv("FIREBASE_CONFIG"); // Render gives the file path

        try(FileInputStream serviceAccount = new FileInputStream(firebaseConfigPath)){
        //try(FileInputStream serviceAccount = new FileInputStream(firebaseConfigPath)){
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        }
    }
}
