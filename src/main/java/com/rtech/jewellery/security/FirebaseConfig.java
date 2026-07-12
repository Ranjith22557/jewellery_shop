package com.rtech.jewellery.security;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

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
        if (jsonFromEnv != null && !jsonFromEnv.isBlank()) {
            return new ByteArrayInputStream(jsonFromEnv.getBytes(StandardCharsets.UTF_8));
        }
        return new FileInputStream("src/main/resources/jewellery.json");
    }
}
