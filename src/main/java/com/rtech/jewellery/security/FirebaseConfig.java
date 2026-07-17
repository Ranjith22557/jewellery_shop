package com.rtech.jewellery.security;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    private static final Logger log = LoggerFactory.getLogger(FirebaseConfig.class);

    @PostConstruct
    public void initializeFirebase() throws IOException {
        InputStream serviceAccount = resolveCredentialsStream();

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }

    /**
     * On a real server there is no "src/main/resources/jewellery.json" file
     * (it's gitignored and never uploaded), so credentials must come from an
     * environment variable there. Locally, the raw json file still works so
     * existing local dev setups keep working unchanged.
     *
     * Deployment: set FIREBASE_SERVICE_ACCOUNT_JSON to the *entire contents*
     * of the Firebase service account JSON file, as a single env var value.
     */
    private InputStream resolveCredentialsStream() throws IOException {
        String jsonFromEnv = System.getenv("FIREBASE_SERVICE_ACCOUNT_JSON");

        // Diagnostic only: logs presence/length, never the actual secret value.
        log.info("FIREBASE_SERVICE_ACCOUNT_JSON present: {}, length: {}",
                jsonFromEnv != null, jsonFromEnv != null ? jsonFromEnv.length() : 0);

        if (jsonFromEnv != null && !jsonFromEnv.isBlank()) {
            return new ByteArrayInputStream(jsonFromEnv.getBytes(StandardCharsets.UTF_8));
        }

        File localFile = new File("src/main/resources/jewellery.json");
        log.info("Falling back to local file, exists: {}", localFile.exists());
        if (localFile.exists()) {
            return new FileInputStream(localFile);
        }

        throw new IllegalStateException(
            "Firebase credentials not found. Set the FIREBASE_SERVICE_ACCOUNT_JSON " +
            "environment variable to the full contents of your Firebase service " +
            "account JSON file (this is required on any deployed server, since " +
            "src/main/resources/jewellery.json only exists on local machines)."
        );
    }
}